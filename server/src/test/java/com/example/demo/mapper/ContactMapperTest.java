package com.example.demo.mapper;

import static com.example.demo.util.DateBefore.*;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureMybatis
@Rollback(value = false)
@TestMethodOrder(OrderAnnotation.class)
public class ContactMapperTest implements WithAssertions {

  @Autowired
  ContactMapper underTest;

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  TagsRepository tagsRepo;

  @Autowired
  ContactRepository contactRepo;

  @Autowired
  CompanyRepository companyRepo;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();

    Boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));
    Boolean hasData = saleRepo.findAll().size() > 0;

    if (hasData || requireEmptyData) return;

    Sale sale1 = new Sale(
      "first_name1",
      "last_name1",
      "password",
      ApplicationUserRole.SALE_ADMIN
    );

    Sale sale2 = new Sale(
      "first_name2",
      "last_name2",
      "password",
      ApplicationUserRole.SALE_ADMIN
    );

    Company company1 = new Company(
      sale1,
      "company1",
      "guangzhou",
      10,
      "consumer",
      "GD"
    );

    Company company2 = new Company(
      sale1,
      "company2",
      "guangzhou",
      20,
      "consumer",
      "GD"
    );

    sale1.setCompanies(Set.of(company1));
    sale2.setCompanies(Set.of(company2));

    saleRepo.save(sale1);
    saleRepo.save(sale2);

    Contact contact1 = new Contact(
      "contact1_FN",
      "contact1_LN",
      "title1",
      "status1",
      daysBefore(5),
      company1,
      sale1,
      "background1"
    );

    Contact contact2 = new Contact(
      "contact2_FN",
      "contact2_LN",
      "title2",
      "status2",
      daysBefore(10),
      company2,
      sale2,
      "background2"
    );

    Tags tag1 = new Tags("tag1", "color1");
    Tags tag2 = new Tags("tag2", "color2");

    tag1.addContact(contact1);
    tag2.addContact(contact2);

    tagsRepo.saveAll(List.of(tag1, tag2));

    contact1.addTags(tag1);
    contact2.addTags(tag2);

    contactRepo.saveAll(List.of(contact1, contact2));
  }

  @Test
  void test_get_contact_by_id() {
    Contact actual = underTest.getContactById(1L);
    assertThat(actual).isNotNull();
  }

  @Test
  @Order(1)
  @Tag("requireEmptyData")
  void test_get_contact_by_id_should_find_null() {
    Contact actual = underTest.getContactById(1L);
    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("providerForGetCompanyContacts")
  void test_get_company_contacts(
    Integer start,
    Integer take,
    String sort,
    String order,
    String status,
    String tags,
    Long sales_id,
    String last_seen_gte,
    String last_seen_lte,
    Long company_id,
    String query
  ) {
    List<Contact> actual = underTest
        .getCompanyContacts( start, take, sort, order, status, tags, sales_id, last_seen_gte, last_seen_lte, company_id, query);

    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetCompanyContacts() {
    return Stream.of(
      // test start & take
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, null, null),
      Arguments.of( 1, 100, "id", "desc", null, null, null, null, null, null, null),
      Arguments.of( 0, 99, "id", "desc", null, null, null, null, null, null, null),
      // test order & sort
      Arguments.of( 0, 100, "id", "asc", null, null, null, null, null, null, null),
      Arguments.of( 0, 100, "status", "desc", null, null, null, null, null, null, null),
      Arguments.of( 0, 100, "status", "asc", null, null, null, null, null, null, null),
      // status
      Arguments.of( 0, 100, "id", "desc", "status1", null, null, null, null, null, null),
      Arguments.of( 0, 100, "id", "desc", "status2", null, null, null, null, null, null),
      // tags
      Arguments.of( 0, 100, "id", "desc", null, "1", null, null, null, null, null),
      Arguments.of( 0, 100, "id", "desc", null, "2", null, null, null, null, null),
      // sales_id
      Arguments.of( 0, 100, "id", "desc", null, null, 1L, null, null, null, null),
      Arguments.of( 0, 100, "id", "desc", null, null, 2L, null, null, null, null),
      // last_seen_gte
      Arguments.of( 0, 100, "id", "desc", null, null, null, daysStringBefore(7), null, null, null),
      // last_seen_lte
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, daysStringBefore(7), null, null),
      // company_id .
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, 1L, null),
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, 2L, null),
      // query
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, null, "back"),
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, null, "titl"),
      Arguments.of( 0, 100, "id", "desc", null, null, null, null, null, null, "_FN")
    );
  }

  @Test
  @Order(2)
  @Tag("requireEmptyData")
  void test_get_companies_count_should_find_null() {
    List<Contact> actual = underTest
        .getCompanyContacts( 0, 100, "id", "desc", null, null, null, null, null, null, null);

    assertThat(actual).isEmpty();
  }

  @ParameterizedTest
  @MethodSource("providerForGetContactCount")
  void test_get_contact_count(
    String status,
    String tags,
    Long sales_id,
    String last_seen_gte,
    String last_seen_lte,
    Long company_id,
    String query
  ) {
    String actual = underTest
        .getContactCount( status, tags, sales_id, company_id, last_seen_gte, last_seen_lte, query);

    assertThat(Integer.parseInt(actual)).isGreaterThan(0);
  }

  private static Stream<Arguments> providerForGetContactCount() {
    return Stream.of(
      Arguments.of(null, null, null, null, null, null, null),
      Arguments.of("status1", null, null, null, null, null, null),
      Arguments.of("status2", null, null, null, null, null, null),
      // tags
      Arguments.of(null, "1", null, null, null, null, null),
      Arguments.of(null, "2", null, null, null, null, null),
      // sales_id
      Arguments.of(null, null, 1L, null, null, null, null),
      Arguments.of(null, null, 2L, null, null, null, null),
      // last_seen_gte
      Arguments.of(null, null, null, daysStringBefore(7), null, null, null),
      // last_seen_lte
      Arguments.of(null, null, null, null, daysStringBefore(7), null, null),
      // company_id
      Arguments.of(null, null, null, null, null, 1L, null),
      Arguments.of(null, null, null, null, null, 2L, null),
      // query
      Arguments.of(null, null, null, null, null, null, "back"),
      Arguments.of(null, null, null, null, null, null, "titl"),
      Arguments.of(null, null, null, null, null, null, "_FN")
    );
  }

  @Test
  @Order(3)
  @Tag("requireEmptyData")
  void test_get_contact_count_should_find_null() {
    String actual = underTest
        .getContactCount( null, null, null, null, null, null, null);

    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("providerForGetContactsByIds")
  void test_get_contact_by_ids(List<Long> ids) {
    List<Contact> actual = underTest.getContactsByIds(ids);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetContactsByIds() {
    return Stream.of(
      Arguments.of(List.of(1L)),
      Arguments.of(List.of(2L)),
      Arguments.of(List.of(1L, 2L))
    );
  }

  @Test
  @Order(4)
  @Tag("requireEmptyData")
  void test_get_contact_by_ids_should_find_null() {
    List<Contact> actual = underTest.getContactsByIds(List.of(1L));
    assertThat(actual).isEmpty();
  }
}

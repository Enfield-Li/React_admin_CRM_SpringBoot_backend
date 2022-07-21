package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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
    boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));

    Boolean hasData = saleRepo.findAll().size() > 0;

    if (hasData || requireEmptyData) {
      if (hasData && requireEmptyData) {
        tagsRepo.deleteAll();
        contactRepo.deleteAll();
        saleRepo.deleteAll();
        companyRepo.deleteAll();
      }
      return;
    }

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
      daysBefore(1),
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
      company1,
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

  private Date daysBefore(Integer days) {
    return Date.from(Instant.now().minus(Duration.ofDays(days)));
  }

  @Test
  void testData() {
    // Contact_Tag ct = underTest.experiment();
    // System.out.println("************** acutal:" + ct.toString());

    System.out.println("************** acutal:" + tagsRepo.findAll());
  }

  @Test
  void testGetContactById() {
    Contact actual = underTest.getContactById(1L);
    assertThat(actual).isNotNull();
  }

  @Test
  @Tag("requireEmptyData")
  void testGetContactByIdNull() {
    Contact actual = underTest.getContactById(1L);
    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("providerForGetContactCount")
  void testGetCompanyContacts(
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
    List<Contact> actual = underTest.getCompanyContacts(
      start,
      take,
      sort,
      order,
      status,
      tags,
      sales_id,
      last_seen_gte,
      last_seen_lte,
      company_id,
      query
    );
    System.out.println("****************** size: " + actual.size());

    assertThat(actual ).isNotEmpty();

  }

  @Test
  void testGetContactCount() {}

  private static Stream<Arguments> providerForGetContactCount() {
    return Stream.of(
      // test start & take
      Arguments.of(0, 100, "id", "desc", null, null, null, null, null, null, null),
      Arguments.of(1, 100, "id", "desc", null, null, null, null, null, null, null),
      Arguments.of(0, 99, "id", "desc", null, null, null, null, null, null, null),
      // test order & sort
      Arguments.of(0, 100, "id", "asc", null, null, null, null, null, null, null),
      Arguments.of(0, 100, "id", "desc", null, null, null, null, null, null, null),
      Arguments.of(0, 100, "id", "asc", null, null, null, null, null, null, null)
    );
  }

  @Test
  void testGetContactsByIds() {}
}

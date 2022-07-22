package com.example.demo.mapper;

import static com.example.demo.util.Before.*;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.DealRespository;
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
public class DealMapperTest implements WithAssertions {

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  ContactRepository contactRepo;

  @Autowired
  TagsRepository tagsRepo;

  @Autowired
  DealRespository dealRepo;

  @Autowired
  DealMapper underTest;

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

    contact1.addTags(tag1);
    contact2.addTags(tag2);

    Deal deal1 = new Deal(
      "deal1",
      "description1",
      "stage1",
      "type1",
      5000L,
      sale1,
      company1
    );
    Deal deal2 = new Deal(
      "deal2",
      "description2",
      "stage2",
      "type2",
      10000L,
      sale2,
      company2
    );

    contact1.addDeals(deal1);
    contact2.addDeals(deal2);

    deal1.addContact(contact1);
    deal2.addContact(contact2);

    saleRepo.saveAll(List.of(sale1, sale2));
    tagsRepo.saveAll(List.of(tag1, tag2));
    contactRepo.saveAll(List.of(contact1, contact2));
    dealRepo.saveAll(List.of(deal1, deal2));
  }

  @Order(5)
  @ParameterizedTest
  @MethodSource("providerForGetCompanyDeals")
  void test_get_company_deals(
    Integer start,
    Integer take,
    String sort,
    String order,
    String type,
    String query,
    Long sales_id,
    Long company_id,
    String stage
  ) {
    List<Deal> actual = underTest
        .getCompanyDeals(start, take, sort, order, type, query, sales_id, company_id, stage);

    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetCompanyDeals() {
    return Stream.of(
      // start & take
      Arguments.of(0, 100, "id", "desc", null, null, null, null, null),
      Arguments.of(1, 100, "id", "desc", null, null, null, null, null),
      Arguments.of(0, 99, "id", "desc", null, null, null, null, null),
      // order & sort
      Arguments.of(0, 100, "id", "asc", null, null, null, null, null),
      Arguments.of(0, 100, "name", "asc", null, null, null, null, null),
      Arguments.of(0, 100, "name", "desc", null, null, null, null, null),
      // type
      Arguments.of(0, 100, "id", "desc", "type1", null, null, null, null),
      Arguments.of(0, 100, "id", "desc", "type2", null, null, null, null),
      // sales_id
      Arguments.of(0, 100, "id", "desc", null, null, 1L, null, null),
      Arguments.of(0, 100, "id", "desc", null, null, 2L, null, null),
      // company_id
      Arguments.of(0, 100, "id", "desc", null, null, null, 1L, null),
      Arguments.of(0, 100, "id", "desc", null, null, null, 2L, null),
      // stage
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "stage1"),
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "stage2"),
      // query
      Arguments.of(0, 100, "id", "desc", null, "dea", null, null, null),
      Arguments.of(0, 100, "id", "desc", null, "descri", null, null, null)
    );
  }

  @Test
  @Order(1)
  @Tag("requireEmptyData")
  void test_get_company_deals_should_find_null() {
    List<Deal> actual = underTest
        .getCompanyDeals(0, 100, "id", "desc", null, null, null, null, null);

    assertThat(actual).isEmpty();
  }

  @ParameterizedTest
  @MethodSource("providerForGetDealCount")
  void test_get_deal_count(
    String type,
    String query,
    Long sales_id,
    Long company_id,
    String stage
  ) {
    String actual = underTest
        .getDealCount(type, query, sales_id, company_id, stage);

    assertThat(Integer.parseInt(actual)).isGreaterThan(0);
  }

  private static Stream<Arguments> providerForGetDealCount() {
    return Stream.of(
      // default
      Arguments.of(null, null, null, null, null),
      // type
      Arguments.of("type1", null, null, null, null),
      Arguments.of("type2", null, null, null, null),
      // sales_id
      Arguments.of(null, null, 1L, null, null),
      Arguments.of(null, null, 2L, null, null),
      // company_id
      Arguments.of(null, null, null, 1L, null),
      Arguments.of(null, null, null, 2L, null),
      // stage
      Arguments.of(null, null, null, null, "stage1"),
      Arguments.of(null, null, null, null, "stage2"),
      // query
      Arguments.of(null, "dea", null, null, null),
      Arguments.of(null, "descri", null, null, null)
    );
  }

  @Test
  @Order(2)
  @Tag("requireEmptyData")
  void test_get_deal_count_should_find_null() {
    String actual = underTest.getDealCount(null, null, null, null, null);
    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("providerForGetDealsPreference")
  void test_get_deals_reference(List<Long> ids) {
    List<Deal> actual = underTest.getDealsReference(ids);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetDealsPreference() {
    return Stream.of(
      Arguments.of(List.of(1L)),
      Arguments.of(List.of(2L)),
      Arguments.of(List.of(1L, 2L))
    );
  }

  @Test
  @Order(3)
  @Tag("requireEmptyData")
  void test_get_deals_reference_should_find_null() {
    List<Deal> actual = underTest.getDealsReference(List.of(1L));
    assertThat(actual).isEmpty();
  }

  @Test
  void test_update_deal_status() {
    Integer actual = underTest.updateDealStatus(1L, "updated");
    assertThat(actual).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Tag("requireEmptyData")
  void test_update_deal_status_should_find_null() {
    Integer actual = underTest.updateDealStatus(1L, "updated");
    assertThat(actual).isEqualTo(0);
  }
}

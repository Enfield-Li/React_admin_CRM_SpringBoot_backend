package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
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
public class companyMapperTest implements WithAssertions {

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  companyMapper underTest;

  @BeforeEach
  void setUp(final TestInfo info) {
    // exclude method https://stackoverflow.com/a/69825777/16648127
    final Set<String> testTags = info.getTags();

    Boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));
    Boolean hasData = saleRepo.findAll().size() > 0;

    if (hasData || requireEmptyData) {
      if (hasData && requireEmptyData) {
        saleRepo.deleteAll();
        underTest.deleteAll();
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

    Sale sale3 = new Sale(
      "first_name3",
      "last_name3",
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

    Company company3 = new Company(
      sale2,
      "company3",
      "shenzhen",
      10,
      "sport",
      "GD"
    );

    Company company4 = new Company(
      sale2,
      "company4",
      "shenzhen",
      20,
      "sport",
      "GD"
    );

    sale1.setCompanies(Set.of(company1, company2));
    sale2.setCompanies(Set.of(company3, company4));

    saleRepo.saveAll(List.of(sale1, sale2, sale3));
  }

  @ParameterizedTest
  @MethodSource("providerForGetFilteredCompanies")
  void testGetFilteredCompanies(
    Integer start,
    Integer take,
    String sort,
    String order,
    Long sales_id,
    Integer minSize,
    Integer maxSize,
    String sector,
    String query
  ) {
    List<Company> actual1 = underTest.getFilteredCompanies(
      start,
      take,
      sort,
      order,
      sales_id,
      minSize,
      maxSize,
      sector,
      query
    );

    assertThat(actual1).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetFilteredCompanies() {
    return Stream.of(
      // test start & take
      Arguments.of(0, 100, "id", "desc", null, null, null, null, null),
      Arguments.of(1, 100, "id", "desc", null, null, null, null, null),
      Arguments.of(0, 99, "id", "desc", null, null, null, null, null),
      // test order & sort
      Arguments.of(0, 100, "id", "asc", null, null, null, null, null),
      Arguments.of(0, 100, "name", "asc", null, null, null, null, null),
      Arguments.of(0, 100, "name", "desc", null, null, null, null, null),
      // test minSize & maxSize
      Arguments.of(0, 100, "id", "desc", null, null, 20, null, null),
      Arguments.of(0, 100, "id", "desc", null, 0, null, null, null),
      Arguments.of(0, 100, "id", "desc", null, 0, 10, null, null),
      Arguments.of(0, 100, "id", "desc", null, 10, 20, null, null),
      Arguments.of(0, 100, "id", "desc", null, 20, 30, null, null),
      // sector
      Arguments.of(0, 100, "id", "desc", null, null, null, "sport", null),
      Arguments.of(0, 100, "id", "desc", null, null, null, "consumer", null),
      // sales_id bug
      Arguments.of(0, 100, "id", "desc", 1L, null, null, null, null),
      Arguments.of(0, 100, "id", "desc", 2L, null, null, null, null),
      // test query
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "comp"), // company name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "company1"), // company full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "shenz"), // city
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "shenzhen"), // city full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "consum"), // sector
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "consumer"), // sector full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "GD"), // state abbriviation
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "G") // state abbriviation full name
    );
  }

  @ParameterizedTest
  @MethodSource("providerForGetCompaniesCount")
  void testGetCompaniesCount(
    String query,
    Long sales_id,
    Integer minSize,
    Integer maxSize,
    String sector
  ) {
    String actual = underTest.getCompaniesCount(
      query,
      sales_id,
      minSize,
      maxSize,
      sector
    );

    assertThat(Integer.parseInt(actual)).isGreaterThan(0);
  }

  private static Stream<Arguments> providerForGetCompaniesCount() {
    return Stream.of(
      // default case
      Arguments.of(null, null, null, null, null),
      // test query
      Arguments.of("comp", null, null, null, null),
      Arguments.of("company1", null, null, null, null),
      Arguments.of("guangz", null, null, null, null),
      Arguments.of("guangzhou", null, null, null, null),
      Arguments.of("consum", null, null, null, null),
      Arguments.of("consumer", null, null, null, null),
      Arguments.of("G", null, null, null, null),
      Arguments.of("GD", null, null, null, null),
      // test sales_id
      Arguments.of(null, 1L, null, null, null),
      Arguments.of(null, 2L, null, null, null),
      // test size
      Arguments.of(null, null, null, 20, null),
      Arguments.of(null, null, 0, null, null),
      Arguments.of(null, null, 0, 20, null),
      // test sector
      Arguments.of(null, null, null, null, "sport"),
      Arguments.of(null, null, null, null, "consumer")
    );
  }

  @ParameterizedTest
  @MethodSource("providerForGetManyReferences")
  void getManyReferences(List<Long> ids) {
    List<Company> actual = underTest.getManyReferences(ids);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetManyReferences() {
    return Stream.of(
      Arguments.of(List.of(1L)),
      Arguments.of(List.of(2L)),
      Arguments.of(List.of(1L, 2L))
    );
  }

  /*
   * Find null
   */
  @Test
  @Tag("requireEmptyData")
  void testGetFilteredCompaniesShouldFindNull() {
    List<Company> actual1 = underTest.getFilteredCompanies(
      0,
      100,
      "id",
      "desc",
      null,
      null,
      null,
      null,
      null
    );

    assertThat(actual1).isEmpty();
  }

  @Test
  @Tag("requireEmptyData")
  void testGetManyReferencesShouldFindNull() {
    List<Company> actual = underTest.getManyReferences(List.of(1L));
    assertThat(actual).isEmpty();
  }

  @Test
  @Tag("requireEmptyData")
  void testGetCompaniesCountShouldFindNull() {
    String actual = underTest.getCompaniesCount(null, null, null, null, null);
    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }
}

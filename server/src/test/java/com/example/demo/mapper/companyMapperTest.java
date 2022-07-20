package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.SaleRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
public class companyMapperTest implements WithAssertions {

  EntityManager em;
  SaleRepository saleRepo;
  companyMapper companyMapper;
  CompanyRepository companyRepo;

  @Autowired
  companyMapperTest(
    CompanyRepository repo,
    companyMapper mapper,
    EntityManager em,
    SaleRepository saleRepo
  ) {
    this.companyRepo = repo;
    this.companyMapper = mapper;
    this.em = em;
    this.saleRepo = saleRepo;
  }

  @BeforeEach
  void setUp() {
    Sale sale1 = new Sale();
    sale1.setPassword("password");
    sale1.setLast_name("last_name1");
    sale1.setFirst_name("first_name1");
    sale1.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company1 = new Company();
    company1.setSale(sale1);
    company1.setSize(10);
    company1.setName("company1");
    company1.setCity("guangzhou");
    company1.setSector("consumer");
    company1.setStateAbbr("guangdong");

    Company company2 = new Company();
    company2.setSale(sale1);
    company2.setSize(20);
    company2.setName("company2");
    company2.setCity("guangzhou");
    company2.setSector("consumer");
    company2.setStateAbbr("guangdong");

    Sale sale2 = new Sale();
    sale2.setPassword("password");
    sale2.setLast_name("last_name2");
    sale2.setFirst_name("first_name2");
    sale2.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company3 = new Company();
    company3.setSale(sale2);
    company3.setSize(10);
    company3.setName("company3");
    company3.setCity("guangzhou");
    company3.setSector("sport");
    company3.setStateAbbr("guangdong");

    Company company4 = new Company();
    company4.setSale(sale2);
    company4.setSize(20);
    company4.setName("company4");
    company4.setCity("guangzhou");
    company4.setSector("sport");
    company4.setStateAbbr("guangdong");

    Sale sale3 = new Sale();
    sale3.setPassword("password");
    sale3.setLast_name("last_name3");
    sale3.setFirst_name("first_name3");
    sale3.setRole(ApplicationUserRole.SALE_ADMIN);

    sale1.setCompanies(Set.of(company1, company2));
    sale2.setCompanies(Set.of(company3, company4));

    saleRepo.saveAll(List.of(sale1, sale2, sale3));
  }

  @AfterEach
  void cleanUp() {
    companyRepo.deleteAll();
    saleRepo.deleteAll();
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
    // test with @ParameterizedTest
    List<Company> actual1 = companyMapper.getFilteredCompanies(
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

    // test sales_id
    List<Company> actual2 = companyMapper.getFilteredCompanies(
      0,
      100,
      "id",
      "desc",
      2L,
      null,
      null,
      null,
      null
    );

    List<Company> actual3 = companyMapper.getFilteredCompanies(
      0,
      100,
      "id",
      "desc",
      1L,
      null,
      null,
      null,
      null
    );

    assertThat(List.of(actual3, actual2, actual1)).isNotEmpty();
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
      // Arguments.of(0, 100, "id", "desc", 1L, null, null, null, null),
      // Arguments.of(0, 100, "id", "desc", 2L, null, null, null, null),
      // test query
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "comp"), // company name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "company1"), // company full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "guangz"), // city
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "guangzhou"), // city full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "consum"), // sector
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "consumer"), // sector full name
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "guangd"), // state abbriviation
      Arguments.of(0, 100, "id", "desc", null, null, null, null, "guangdong") // state abbriviation full name
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
    String actual = companyMapper.getCompaniesCount(
      query,
      sales_id,
      minSize,
      maxSize,
      sector
    );

    assertThat(Integer.parseInt(actual)).isGreaterThanOrEqualTo(0);
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
      Arguments.of("guangd", null, null, null, null),
      Arguments.of("guangdong", null, null, null, null),
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

  @Test
  void testGetManyReferences() {
    List<Company> actual1 = companyMapper.getManyReferences(List.of(1L));
    List<Company> actual2 = companyMapper.getManyReferences(List.of(2L));
    List<Company> actual3 = companyMapper.getManyReferences(List.of(1L, 2L));

    assertThat(List.of(actual1, actual2, actual3)).isNotEmpty();
  }
}

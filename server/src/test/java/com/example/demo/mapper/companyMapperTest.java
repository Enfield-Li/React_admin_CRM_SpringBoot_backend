package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.SaleRepository;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@AutoConfigureMybatis
public class companyMapperTest implements WithAssertions {

  @Autowired
  CompanyRepository repo;

  @Autowired
  companyMapper mapper;

  @Autowired
  EntityManager em;

  @Autowired
  SaleRepository saleRepo;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

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

    sale1.setCompanies(Set.of(company1, company2));
    sale2.setCompanies(Set.of(company3, company4));

    saleRepo.saveAll(List.of(sale1, sale2));
  }

  @AfterEach
  void cleanUp() {
    repo.deleteAll();
    saleRepo.deleteAll();
  }

  @Test
  void getAll() {
    List<Company> actual = repo.findAll();

    System.out.println("**************" + actual.size());
  }

  @ParameterizedTest
  @MethodSource("testone")
  void findOne(
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
    List<Company> actual = mapper.getFilteredCompanies(
      0,
      2,
      "id",
      "desc",
      1L,
      0,
      20,
      "consumer",
      null
    );

    // System.out.println("******************** size: " + actual.size());
    assertThat(actual).isNotEmpty();
  }

  @ParameterizedTest
  @MethodSource("provideParamsForGetFilteredCompanies")
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
    List<Company> actual = mapper.getFilteredCompanies(
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

    actual.forEach(c -> System.out.println(c.toString()));
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> provideParamsForGetFilteredCompanies() {
    return Stream.of(
      // test start & take
      Arguments.of(0, 2, "id", "desc", null, 0, 20, null, null),
      Arguments.of(1, 2, "id", "desc", null, 0, 20, null, null),
      // test order & sort
      Arguments.of(0, 2, "id", "asc", null, 0, 20, null, null),
      Arguments.of(0, 2, "name", "asc", null, 0, 20, null, null),
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, null),
      // test minSize & maxSize
      Arguments.of(0, 2, "id", "desc", null, null, 20, null, null),
      Arguments.of(0, 2, "id", "desc", null, 0, null, null, null),
      Arguments.of(0, 2, "id", "desc", null, 0, 10, null, null),
      Arguments.of(0, 2, "id", "desc", null, 10, 20, null, null),
      Arguments.of(0, 2, "id", "desc", null, 20, 30, null, null),
      // sector
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "sport", null),
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "consumer", null),
      // // sales_id
      // Arguments.of(0, 2, "id", "desc", 1L, 0, 20, null, null),
      // Arguments.of(0, 2, "id", "desc", 2L, 0, 20, null, null),
      // test query
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "comp"), // company name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "company1"), // company full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "guangz"), // city
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "guangzhou"), // city full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "consum"), // sector
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "consumer"), // sector full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "guangd"), // state abbriviation
      Arguments.of(0, 2, "name", "desc", null, 0, 20, null, "guangdong") // state abbriviation full name
    );
  }

  private static Stream<Arguments> testone() {
    return Stream.of(
      Arguments.of(0, 2, "id", "desc", 1L, 0, 20, null, null),
      Arguments.of(0, 2, "id", "desc", 2L, 0, 20, null, null)
    );
  }

  @Test
  void testGetManyReferences() {}
}

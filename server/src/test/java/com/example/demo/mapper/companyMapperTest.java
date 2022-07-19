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
    Sale sale = new Sale();
    sale.setId(1L);
    sale.setPassword("password");
    sale.setLast_name("last_name");
    sale.setFirst_name("first_name");
    sale.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company1 = new Company();
    company1.setSize(10);
    company1.setSale(sale);
    company1.setSales_id(1L);
    company1.setName("company1");
    company1.setCity("guangzhou");
    company1.setSector("consumer");
    company1.setStateAbbr("guangdong");

    Company company2 = new Company();
    company2.setSize(20);
    company2.setName("company2");
    company2.setCity("guangzhou");
    company2.setSector("consumer");
    company2.setStateAbbr("guangdong");

    sale.setCompanies(Set.of(company1, company2));
    saleRepo.save(sale);
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

    // actual.forEach(c -> System.out.println(c.toString()));
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
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "consumer", null),
      Arguments.of(1, 2, "id", "desc", null, 0, 20, "consumer", null),
      // test order & sort
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "consumer", null),
      Arguments.of(0, 2, "id", "asc", null, 0, 20, "consumer", null),
      Arguments.of(0, 2, "name", "asc", null, 0, 20, "consumer", null),
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", null),
      // test sales_id
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "consumer", null),
      Arguments.of(0, 2, "id", "desc", 1L, 0, 20, "consumer", null),
      // test minSize & maxSize
      Arguments.of(0, 2, "id", "desc", null, null, 20, "consumer", null),
      Arguments.of(0, 2, "id", "desc", null, 0, null, "consumer", null),
      Arguments.of(0, 2, "id", "desc", null, 0, 10, "consumer", null),
      Arguments.of(0, 2, "id", "desc", null, 0, 20, "consumer", null),
      Arguments.of(0, 2, "id", "desc", null, 10, 20, "consumer", null),
      Arguments.of(0, 2, "id", "desc", null, 20, 30, "consumer", null),
      // test query
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "comp"), // company name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "company1"), // company full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "guangz"), // city
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "guangzhou"), // city full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "consum"), // sector
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "consumer"), // sector full name
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "guangd"), // state abbriviation
      Arguments.of(0, 2, "name", "desc", null, 0, 20, "consumer", "guangdong") // state abbriviation full name
    );
  }

  private static Stream<Arguments> testone() {
    return Stream.of(
      Arguments.of(0, 2, "id", "desc", 1L, 0, 20, "consumer", null)
    );
  }

  @Test
  void testGetManyReferences() {}
}

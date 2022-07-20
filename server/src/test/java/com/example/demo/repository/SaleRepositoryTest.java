package com.example.demo.repository;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import java.util.Optional;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SaleRepositoryTest implements WithAssertions {

  SaleRepository saleRepositoryTest;

  @Autowired
  SaleRepositoryTest(SaleRepository saleRepositoryTest) {
    this.saleRepositoryTest = saleRepositoryTest;
  }

  @AfterEach
  void cleanUp() {
    saleRepositoryTest.deleteAll();
  }

  @Test
  void findByFullNameOnFullNameShouldPass() {
    // given
    String first_name = "Enfield";
    String last_name = "Li";
    String fullName = first_name + " " + last_name;

    Sale sale = new Sale(
      "email@gg.com",
      "stats",
      first_name,
      last_name,
      "123",
      ApplicationUserRole.SALE_PERSON
    );

    saleRepositoryTest.save(sale);

    // when
    Optional<Sale> saleFound = saleRepositoryTest.findByFullName(fullName);

    // then
    assertThat(saleFound).isNotEmpty();
  }

  @Test
  void findByFullNameOnlyFirstNameShouldPass() {
    // given
    String first_name = "Enfield";
    String last_name = null;
    String fullName = first_name;

    Sale sale = new Sale(
      "email@gg.com",
      "stats",
      first_name,
      last_name,
      "123",
      ApplicationUserRole.SALE_PERSON
    );

    saleRepositoryTest.save(sale);

    // when
    Optional<Sale> saleFound = saleRepositoryTest.findByFullName(fullName);

    // then
    assertThat(saleFound).isNotEmpty();
  }

  @Test
  void findByFullNameOnFullNameShouldFail() {
    // given
    // when
    Optional<Sale> saleFound = saleRepositoryTest.findByFullName("Abc");
    Optional<Sale> saleFound2 = saleRepositoryTest.findByFullName("Abc Def");

    // then
    assertThat(saleFound).isEmpty();
    assertThat(saleFound2).isEmpty();
  }
}

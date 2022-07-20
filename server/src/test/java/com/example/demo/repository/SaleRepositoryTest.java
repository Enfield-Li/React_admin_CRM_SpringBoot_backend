package com.example.demo.repository;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SaleRepositoryTest implements WithAssertions {

  @Autowired
  SaleRepository underTest;

  private String isFullName = "first_name last_name";
  private String isNotFullName = "only_First_Name";

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();
    if (testTags.stream().anyMatch(tag -> tag.equals("skipBeforeEach"))) return;

    Sale isFullNameSale = new Sale(
      "first_name",
      "last_name",
      "password",
      ApplicationUserRole.SALE_ADMIN
    );

    Sale isNotFullNameSale = new Sale(
      "email@gg.com",
      "stats",
      "only_First_Name",
      null,
      "123",
      ApplicationUserRole.SALE_PERSON
    );

    underTest.saveAll(Set.of(isFullNameSale, isNotFullNameSale));
  }

  @AfterEach
  void cleanUp() {
    underTest.deleteAll();
  }

  @Test
  void findByFullNameOnFullNameShouldPass() {
    Optional<Sale> saleFound = underTest.findByFullName(isFullName);
    assertThat(saleFound).isNotEmpty();
  }

  @Test
  void findByFullNameOnlyFirstNameShouldPass() {
    Optional<Sale> saleFound = underTest.findByFullName(isNotFullName);
    assertThat(saleFound).isNotEmpty();
  }

  @Test
  @Tag("skipBeforeEach")
  void findByFullNameOnFullNameShouldFail() {
    Optional<Sale> saleFound = underTest.findByFullName("Abc");
    Optional<Sale> saleFound2 = underTest.findByFullName("Abc Def");

    assertThat(saleFound).isEmpty();
    assertThat(saleFound2).isEmpty();
  }
}

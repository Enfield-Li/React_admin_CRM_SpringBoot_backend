package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
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

@DataJpaTest
@AutoConfigureMybatis
public class SaleMapperTest implements WithAssertions {

  @Autowired
  SaleMapper saleMapper;

  @Autowired
  SaleRepository saleRepo;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();
    if (testTags.stream().anyMatch(tag -> tag.equals("skipBeforeEach"))) return;

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

    saleRepo.saveAll(Set.of(sale1, sale2, sale3));
  }

  @AfterEach
  void cleanUp() {
    saleRepo.deleteAll();
  }

  @ParameterizedTest
  @MethodSource("providerForGetAllSales")
  void testGetAllSales(Integer start, Integer take, String sort, String order) {
    List<Sale> actual = saleMapper.getAllSales(start, take, sort, order);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetAllSales() {
    return Stream.of(
      // default case
      Arguments.of(0, 3, "id", "desc"),
      // test start
      Arguments.of(1, 3, "id", "desc"),
      // test take
      Arguments.of(0, 2, "id", "desc"),
      // test sort
      Arguments.of(0, 3, "role", "desc"),
      // test order
      Arguments.of(0, 3, "id", "asc")
    );
  }

  @Test
  void testGetSaleCount() {
    String actual = saleMapper.getSaleCount();
    assertThat(Integer.parseInt(actual)).isEqualTo(3);
  }

  @Test
  @Tag("skipBeforeEach")
  void testGetSaleCountShouldFindNull() {
    String actual = saleMapper.getSaleCount();
    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }

  @ParameterizedTest
  @Tag("skipBeforeEach")
  @MethodSource("providerForGetAllSales")
  void testGetAllSalesShouldFindNull(
    Integer start,
    Integer take,
    String sort,
    String order
  ) {
    List<Sale> actual = saleMapper.getAllSales(start, take, sort, order);
    assertThat(actual).isEmpty();
  }
}

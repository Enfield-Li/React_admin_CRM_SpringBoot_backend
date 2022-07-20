package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import java.util.List;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
  void setUp() {
    Sale sale1 = new Sale();
    sale1.setPassword("password");
    sale1.setLast_name("last_name1");
    sale1.setFirst_name("first_name1");
    sale1.setRole(ApplicationUserRole.SALE_ADMIN);

    Sale sale2 = new Sale();
    sale2.setPassword("password");
    sale2.setLast_name("last_name2");
    sale2.setFirst_name("first_name2");
    sale2.setRole(ApplicationUserRole.SALE_ADMIN);

    Sale sale3 = new Sale();
    sale3.setPassword("password");
    sale3.setLast_name("last_name3");
    sale3.setFirst_name("first_name3");
    sale3.setRole(ApplicationUserRole.SALE_ADMIN);

    saleRepo.saveAll(List.of(sale1, sale2, sale3));
  }

  @AfterEach
  void cleanUp() {
    saleRepo.deleteAll();
  }

  @Test
  void testGetAllSales(
    Integer start,
    Integer take,
    String sort,
    String order
  ) {}

  @Test
  void testGetSaleCount() {
    String actual = saleMapper.getSaleCount();
    assertThat(Integer.parseInt(actual)).isEqualTo(3);
  }
}

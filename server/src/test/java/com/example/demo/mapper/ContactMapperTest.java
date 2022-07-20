package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
public class ContactMapperTest {

  @Autowired
  ContactMapper underTest;

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  ContactRepository contactRepo;

  @Autowired
  CompanyRepository companyRepo;

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

  @Test
  void testGetCompanyContacts() {}

  @Test
  void testGetContactById() {}

  @Test
  void testGetContactCount() {}

  @Test
  void testGetContactsByIds() {}
}

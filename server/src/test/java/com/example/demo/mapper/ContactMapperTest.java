package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;

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
  TagsRepository tagsRepo;

  // @Autowired
  // ContactRepository contactRepo;

  // @Autowired
  // CompanyRepository companyRepo;

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

    Tags tag1 = new Tags();
    Contact contact1 = new Contact();

    sale1.setCompanies(Set.of(company1, company2, company3));

    saleRepo.saveAll(Set.of(sale1, sale2));
  }

  @AfterEach
  void cleanUp() {
    saleRepo.deleteAll();
  }

  @Test 
  void testData() {}
  
  @Test
  void testGetCompanyContacts() {}

  @Test
  void testGetContactById() {}

  @Test
  void testGetContactCount() {}

  @Test
  void testGetContactsByIds() {}
}

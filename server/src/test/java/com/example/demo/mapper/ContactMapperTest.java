package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.dto.Contact_Tag;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureMybatis
@Rollback(value = false)
public class ContactMapperTest implements WithAssertions {

  @Autowired
  ContactMapper underTest;

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  TagsRepository tagsRepo;

  @Autowired
  ContactRepository contactRepo;

  // @Autowired
  // CompanyRepository companyRepo;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();

    if (testTags.stream().anyMatch(tag -> tag.equals("skipBeforeEach"))) return;

    Tags tag1 = new Tags("tag1", "color1");
    Tags tag2 = new Tags("tag2", "color2");

    tagsRepo.saveAll(List.of(tag1, tag2));

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

    sale1.setCompanies(Set.of(company1));
    sale2.setCompanies(Set.of(company2));

    saleRepo.save(sale1);
    saleRepo.save(sale2);

    Contact contact1 = new Contact(
      "contact1_FN",
      "contact1_LN",
      "title1",
      "status1",
      daysBefore(1),
      company1,
      sale1,
      Set.of(tag1),
      "background1"
    );

    Contact contact2 = new Contact(
      "contact2_FN",
      "contact2_LN",
      "title2",
      "status2",
      daysBefore(10),
      company1,
      sale2,
      Set.of(tag1),
      "background2"
    );

    contactRepo.saveAll(List.of(contact1, contact2));
  }

  private Date daysBefore(Integer days) {
    return Date.from(Instant.now().minus(Duration.ofDays(days)));
  }

  @Test
  void testData() {
    // Contact_Tag ct = underTest.experiment();
    // System.out.println("************** acutal:" + ct.toString());

    System.out.println("************** acutal:" + tagsRepo.findAll());
  }

  @Test
  void testGetContactById() {
    Contact actual = underTest.getContactById(1L);
    System.out.println("************** acutal:" + actual);
    assertThat(actual).isNotNull();
    // assertThat(actual.getRaw_tags()).isNotNull();
  }

  @Test
  void testGetCompanyContacts() {}

  @Test
  void testGetContactCount() {}

  @Test
  void testGetContactsByIds() {}
}

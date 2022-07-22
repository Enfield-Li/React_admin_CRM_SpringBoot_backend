package com.example.demo.mapper;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactNote;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactNoteRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureMybatis
@Rollback(value = false)
@TestMethodOrder(OrderAnnotation.class)
public class ContactNoteMapperTest implements WithAssertions {

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  ContactRepository contactRepo;

  @Autowired
  ContactNoteMapper underTest;

  @Autowired
  ContactNoteRepository contactNoteRepo;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();

    Boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));
    Boolean hasData = saleRepo.findAll().size() > 0;

    if (hasData || requireEmptyData) return;

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
      daysBefore(5),
      company1,
      sale1,
      "background1"
    );

    Contact contact2 = new Contact(
      "contact2_FN",
      "contact2_LN",
      "title2",
      "status2",
      daysBefore(10),
      company2,
      sale2,
      "background2"
    );

    contactRepo.saveAll(List.of(contact1, contact2));

    ContactNote ctn1 = new ContactNote("ContactNote1", contact1, sale1);
    ContactNote ctn2 = new ContactNote("ContactNote2", contact2, sale2);

    contactNoteRepo.saveAll(List.of(ctn1, ctn2));
  }

  private static Date daysBefore(Integer days) {
    return Date.from(Instant.now().minus(Duration.ofDays(days)));
  }

  @ParameterizedTest
  @MethodSource("providerForGetAllContactNotes")
  void test_get_all_contact_notes(
    Integer start,
    Integer take,
    String sort,
    String order,
    Long sales_id,
    Long contact_id
  ) {
    List<ContactNote> actual = underTest.getAllContactNotes(
      start,
      take,
      sort,
      order,
      sales_id,
      contact_id
    );

    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetAllContactNotes() {
    return Stream.of(
      // test start & take
      Arguments.of(0, 100, "id", "desc", null, null),
      Arguments.of(1, 100, "id", "desc", null, null),
      Arguments.of(0, 99, "id", "desc", null, null),
      // test order & sort
      Arguments.of(0, 100, "id", "asc", null, null),
      Arguments.of(0, 100, "text", "desc", null, null),
      Arguments.of(0, 100, "text", "asc", null, null),
      // sales_id
      Arguments.of(0, 100, "id", "desc", 1L, null),
      Arguments.of(0, 100, "id", "desc", 2L, null),
      // contact_id
      Arguments.of(0, 100, "id", "desc", null, 1L),
      Arguments.of(0, 100, "id", "desc", null, 2L)
    );
  }

  @Test
  @Order(1)
  @Tag("requireEmptyData")
  void test_get_all_contact_notes_should_find_null() {
    List<ContactNote> actual = underTest.getAllContactNotes(
      0,
      100,
      "id",
      "desc",
      null,
      null
    );

    assertThat(actual).isEmpty();
  }

  @ParameterizedTest
  @MethodSource("providerForGetContactNoteCount")
  void test_get_contact_note_count(Long sales_id, Long contact_id) {
    String actual = underTest.getContactNoteCount(sales_id, contact_id);
    assertThat(Integer.parseInt(actual)).isGreaterThan(0);
  }

  private static Stream<Arguments> providerForGetContactNoteCount() {
    return Stream.of(
      Arguments.of(null, null),
      // sales_id
      Arguments.of(1L, null),
      Arguments.of(2L, null),
      // contact_id
      Arguments.of(null, 1L),
      Arguments.of(null, 2L)
    );
  }

  @Test
  @Order(2)
  @Tag("requireEmptyData")
  void test_get_contact_note_count_should_find_null() {
    String actual = underTest.getContactNoteCount(null, null);
    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }
}

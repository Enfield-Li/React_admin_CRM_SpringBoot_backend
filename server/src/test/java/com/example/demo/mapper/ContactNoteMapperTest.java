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
public class ContactNoteMapperTest {

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  ContactRepository contactRepo;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();

    Boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));
    Boolean hasData = saleRepo.findAll().size() > 0;

    if (hasData || requireEmptyData) return;
  }

  @ParameterizedTest
  @MethodSource("providerForGetAllContactNotes")
  void test_get_all_contact_notes(
    Integer start,
    Integer take,
    String sort,
    String order,
    String sales_id,
    String contact_id
  ) {}

  private static Stream<Arguments> providerForGetAllContactNotes() {
    return Stream.of(
      // test start & take
      Arguments.of(0, 100, "id", "desc", null, null),
      Arguments.of(1, 100, "id", "desc", null, null),
      Arguments.of(0, 99, "id", "desc", null, null),
      // test order & sort
      Arguments.of(0, 100, "id", "desc", null, null),
      Arguments.of(0, 100, "id", "desc", null, null),
      Arguments.of(0, 100, "id", "desc", null, null),
      // sales_id
      Arguments.of(0, 100, "id", "desc", null, null),
      // contact_id
      Arguments.of(0, 100, "id", "desc", null, null),
      Arguments.of(0, 100, "id", "desc", null, null)
       );
  }

  @Test
  @Order(1)
  void test_get_all_contact_notes_should_find_null() {}

  @Test
  void test_get_contact_note_count(String sales_id, String contact_id) {}

  @Test
  @Order(2)
  void test_get_contact_note_count_should_find_null() {}
}

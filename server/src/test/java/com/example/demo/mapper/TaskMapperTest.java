package com.example.demo.mapper;

import static com.example.demo.util.Before.*;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.entity.Task;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import com.example.demo.repository.TaskRepository;
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
public class TaskMapperTest implements WithAssertions {

  @Autowired
  SaleRepository saleRepo;

  @Autowired
  TagsRepository tagsRepo;

  @Autowired
  ContactRepository contactRepo;

  @Autowired
  TaskRepository taskRepo;

  @Autowired
  TaskMapper underTest;

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

    Contact contact1 = new Contact(
      "contact1_FN",
      "contact1_LN",
      "title1",
      "status1",
      daysBefore(5),
      null,
      sale1,
      "background1"
    );

    Contact contact2 = new Contact(
      "contact2_FN",
      "contact2_LN",
      "title2",
      "status2",
      daysBefore(10),
      null,
      sale2,
      "background2"
    );

    Task task1 = new Task("task1", sale1, contact1);
    Task task2 = new Task("task2", sale2, contact2);

    saleRepo.saveAll(List.of(sale1, sale2));
    contactRepo.saveAll(List.of(contact1, contact2));
    taskRepo.saveAll(List.of(task1, task2));
  }

  @ParameterizedTest
  @MethodSource("providerForGetAllTasks")
  void test_get_all_tasks(
    Integer start,
    Integer take,
    String sort,
    String order,
    Long contact_id
  ) {
    List<Task> actual = underTest.getAllTasks(
      start,
      take,
      sort,
      order,
      contact_id
    );
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetAllTasks() {
    return Stream.of(
      // start & take
      Arguments.of(0, 100, "id", "desc", null),
      Arguments.of(1, 100, "id", "desc", null),
      Arguments.of(0, 99, "id", "desc", null),
      // order & sort
      Arguments.of(0, 100, "id", "asc", null),
      Arguments.of(0, 100, "text", "asc", null),
      Arguments.of(0, 100, "text", "desc", null),
      // contact_id
      Arguments.of(0, 100, "id", "desc", 1L),
      Arguments.of(0, 100, "id", "desc", 2L)
    );
  }

  @Test
  @Order(1)
  @Tag("requireEmptyData")
  void test_get_all_tasks_should_find_null() {
    List<Task> actual = underTest.getAllTasks(0, 100, "id", "desc", null);
    assertThat(actual).isEmpty();
  }

  @Test
  void test_get_task_count() {
    String actual = underTest.getTaskCount();
    assertThat(Integer.parseInt(actual)).isGreaterThan(0);
  }

  @Test
  @Order(2)
  @Tag("requireEmptyData")
  void test_get_task_count_should_find_null() {
    String actual = underTest.getTaskCount();
    assertThat(Integer.parseInt(actual)).isEqualTo(0);
  }
}

package com.example.demo.mapper;

import com.example.demo.entity.Tags;
import com.example.demo.repository.TagsRepository;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
// @ResetDatabase
// @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagsMapperTestTwo implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepo;

  // Tags tag1;
  // Tags tag2;
  // Tags tag3;

  @BeforeEach
  void setUp(final TestInfo info) {
    // final Set<String> testTags = info.getTags();
    // if (testTags.stream().anyMatch(tag -> tag.equals("skipBeforeEach"))) return;

    // tag1 = tagsRepo.save(new Tags("tag1", "color1"));
    // tag2 = tagsRepo.save(new Tags("tag2", "color2"));
    // tag3 = tagsRepo.save(new Tags("tag3", "color3"));

    if (tagsRepo.findAll().size() > 0) return;

    Tags tag1 = new Tags("tag1", "color1");
    Tags tag2 = new Tags("tag2", "color2");
    Tags tag3 = new Tags("tag3", "color3");

    tagsRepo.saveAll(List.of(tag1, tag2, tag3));
  }

  // @AfterEach
  // void cleanUp() {
  // tagsRepo.deleteAll();
  // }

  @Test
  void test() {
    Tags actual1 = tagsRepo.findById(1).orElse(null);
    System.out.println("****************** all: " + tagsRepo.findAll());

    assertThat(actual1).isNotNull();
  }

  @Test
  // @Tag("skipBeforeEach")
  void test2() {
    Tags actual2 = tagsRepo.findById(1).orElse(null);
    System.out.println("****************** all: " + tagsRepo.findAll());

    assertThat(actual2).isNotNull();
  }

  @Test
  // @Tag("skipBeforeEach")
  void test3() {
    Tags actual3 = tagsRepo.findById(2).orElse(null);
    System.out.println("****************** all: " + tagsRepo.findAll());

    assertThat(actual3).isNotNull();
  }

  @ParameterizedTest
  @MethodSource("paramProvider")
  void testGetFilteredCompanies(Integer id) {
    Tags actual3 = tagsRepo.findById(id).orElse(null);

    assertThat(actual3).isNotNull();
  }

  private static Stream<Arguments> paramProvider() {
    return Stream.of(
      // test start & take
      Arguments.of(1),
      Arguments.of(1),
      Arguments.of(2)
    );
  }
}

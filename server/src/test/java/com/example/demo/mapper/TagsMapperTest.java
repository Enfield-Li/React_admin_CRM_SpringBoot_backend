package com.example.demo.mapper;

import com.example.demo.entity.Tags;
import com.example.demo.repository.TagsRepository;
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
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureMybatis
@Rollback(value = false)
// http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/#:~:text=Use%20together%20with%20other%20%40***Test
public class TagsMapperTest implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepo;

  @Autowired
  private TagsMapper underTest;

  @BeforeEach
  void setUp(final TestInfo info) {
    final Set<String> testTags = info.getTags();
    boolean requireEmptyData = testTags
      .stream()
      .anyMatch(tag -> tag.equals("requireEmptyData"));

    if (tagsRepo.findAll().size() > 0 || requireEmptyData) {
      if (requireEmptyData) {
        tagsRepo.deleteAll();
      }
      return;
    }

    Tags tag1 = new Tags("tag1", "color1");
    Tags tag2 = new Tags("tag2", "color2");
    Tags tag3 = new Tags("tag3", "color3");

    tagsRepo.saveAll(List.of(tag1, tag2, tag3));
  }

  @ParameterizedTest
  @MethodSource("providerForGetTagsByIds")
  void testGetTagsByIds(List<Integer> ids) {
    List<Tags> actual = underTest.getTagsByIds(ids);
    System.out.println("********************* ids: " + actual);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> providerForGetTagsByIds() {
    return Stream.of(
      Arguments.of(List.of(1)),
      Arguments.of(List.of(2)),
      Arguments.of(List.of(1, 3))
    );
  }

  @Test
  @Tag("requireEmptyData")
  void testGetTagsByIdsShouldFindNull() {
    List<Tags> actual = underTest.getTagsByIds(List.of(1));
    assertThat(actual).isEmpty();
  }
}

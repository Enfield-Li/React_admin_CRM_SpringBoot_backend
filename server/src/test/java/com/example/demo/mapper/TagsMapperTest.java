package com.example.demo.mapper;

import com.example.demo.entity.Tags;
import com.example.demo.repository.TagsRepository;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
// http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/#:~:text=Use%20together%20with%20other%20%40***Test
public class TagsMapperTest implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepo;

  @Autowired
  private TagsMapper tagsMapper;

  @BeforeEach
  void setUp() {
    Tags tag1 = new Tags("tag1", "color1");
    Tags tag2 = new Tags("tag2", "color2");
    Tags tag3 = new Tags("tag3", "color3");

    tagsRepo.saveAll(List.of(tag1, tag2, tag3));
  }

  // @AfterEach
  // void cleanUp() {
  //   tagsRepo.deleteAll();
  // }

  @ParameterizedTest
  @MethodSource("provider")
  void testGetTagsByIds(List<Integer> ids) {
    List<Tags> actual = tagsMapper.getTagsByIds(ids);
    System.out.println("********************* ids: " + actual);
    assertThat(actual).isNotEmpty();
  }

  private static Stream<Arguments> provider() {
    return Stream.of(
      Arguments.of(List.of(2)),
      Arguments.of(List.of(2))
      // ,
      // Arguments.of(List.of(1, 3))
    );
  }

  @ParameterizedTest
  @MethodSource("providerForId")
  void testGetTagsById(Integer id) {
    Tags actual = tagsMapper.getTagsById(id);
    System.out.println("********************* id: " + actual);
    assertThat(actual).isNotNull();
  }

  private static Stream<Arguments> providerForId() {
    return Stream.of(
      Arguments.of(2),
      Arguments.of(2)
      // ,
      // Arguments.of(List.of(1, 3))
    );
  }

  @Test
  void testGetTagsByIdsShouldFindNull() {
    List<Tags> actual = tagsMapper.getTagsByIds(List.of(1, 4));

    assertThat(actual).isEmpty();
  }
}

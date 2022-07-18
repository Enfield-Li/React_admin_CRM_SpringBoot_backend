package com.example.demo.mapper;

import com.example.demo.entity.Tags;
import com.example.demo.repository.TagsRepository;
import java.util.List;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
// http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/#:~:text=Use%20together%20with%20other%20%40***Test
public class TagsMapperTest implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepository;

  @Autowired
  private TagsMapper tagsMapper;

  @Test
  void testGetTagsByIdsThatHaveReturns() {
    // given
    Tags tag1 = new Tags("tag1", "color1", null);
    Tags tag2 = new Tags("tag2", "color2", null);
    Tags tag3 = new Tags("tag3", "color3", null);
    List<Tags> tags = List.of(tag1, tag2, tag3);

    tagsRepository.saveAll(tags);

    // when
    List<Tags> actual = tagsMapper.getTagsByIds(List.of(1, 4));

    // then
    assertThat(actual).isNotNull();
    assertThat(actual).hasSize(1);
  }

  @Test
  void testGetTagsByIdsThatHaveNullReturns() {
    // given
    List<Tags> actual = tagsMapper.getTagsByIds(List.of(1, 4));

    // then
    assertThat(actual).isEmpty();
  }
}

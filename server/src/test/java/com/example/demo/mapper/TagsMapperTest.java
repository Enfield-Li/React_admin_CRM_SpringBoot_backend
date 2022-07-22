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
public class TagsMapperTest implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepo;

  @Autowired
  private TagsMapper underTest;

  @Test
  void test_get_tags_by_ids() {
    tagsRepo.save(new Tags("tag1", "color1"));

    List<Tags> actual = underTest.getTagsByIds(List.of(1));

    assertThat(actual).isNotEmpty();
  }

  @Test
  void test_get_tags_by_ids_should_find_null() {
    List<Tags> actual = underTest.getTagsByIds(List.of(1));
    assertThat(actual).isEmpty();
  }
}

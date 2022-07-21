package com.example.demo.repository;

import com.example.demo.entity.Tags;
import java.util.List;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TagsRepositoryTest implements WithAssertions {

  @Autowired
  private TagsRepository tagsRepo;

  @Test
  void testDeleteTagsByName() {
    Tags tag = new Tags("tag1", "color1");

    tagsRepo.save(tag);
    tagsRepo.deleteTagsByName("tag1");

    List<Tags> actual = tagsRepo.findAll();
    assertThat(actual).isEmpty();
  }

  @Test
  void testDeleteTagsByNameShouldFail() {
    Tags tag = new Tags("tag1", "color1");
    tagsRepo.save(tag);

    tagsRepo.deleteTagsByName("");

    List<Tags> actual = tagsRepo.findAll();
    assertThat(actual.size()).isEqualTo(1);
  }
}

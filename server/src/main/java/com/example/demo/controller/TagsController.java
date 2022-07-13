package com.example.demo.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DeleteTagsDto;
import com.example.demo.entity.Tags;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.TagsMapper;
import com.example.demo.repository.TagsRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Tag")
@RequestMapping("/tags")
class TagsController {

  private final TagsRepository tagsRepo;
  private final TagsMapper tagsMapper;

  public TagsController(TagsRepository tagsRepo, TagsMapper tagsMapper) {
    this.tagsRepo = tagsRepo;
    this.tagsMapper = tagsMapper;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAllTag(@RequestBody List<Tags> tags) {
    tagsRepo.saveAll(tags);
  }

  @PostMapping
  public ResponseEntity<Tags> createTag(@RequestBody Tags tag) {
    Tags savedtag = tagsRepo.save(tag);
    return ResponseEntity.ok(savedtag);
  }

  @GetMapping
  public ResponseEntity<List<Tags>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "q", required = false) String query
  ) {
    List<Tags> tags = tagsRepo.findAll();
    String tagsCount = tags.size() + "";

    return ResponseEntity.ok().header("X-Total-Count", tagsCount).body(tags);
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Tags>> getManyReference(
    @RequestParam("id") List<Long> ids
  ) {
    List<Tags> contacts = tagsMapper.getTagsByIds(ids);

    return ResponseEntity.ok(contacts);
  }

  @Transactional
  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteTag(@RequestBody DeleteTagsDto dto) {
    try {
      if (dto.getId() != null) {
        tagsRepo.deleteById(dto.getId());
      } else {
        tagsRepo.deleteTagsByName(dto.getName());
      }

      return ResponseEntity.ok().build();
    } catch (EmptyResultDataAccessException e) {
      throw new ItemNotFoundException("Tag", dto.getId());
    }
  }
}

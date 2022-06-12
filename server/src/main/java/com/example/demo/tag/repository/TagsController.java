package com.example.demo.tag.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tag.TagsRepository;
import com.example.demo.tag.entity.Tags;

@RestController
@Tag(name = "Tag")
@RequestMapping("/tags")
class TagsController {

  private final TagsRepository tagsRepo;
  private final TagsMapper tagsMapper;

  @Autowired
  public TagsController(TagsRepository tagsRepo, TagsMapper tagsMapper) {
    this.tagsRepo = tagsRepo;
    this.tagsMapper = tagsMapper;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAllTag(@RequestBody List<com.example.demo.tag.entity.Tags> tags) {
    tagsRepo.saveAll(tags);
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

    return ResponseEntity.ok().body(contacts);
  }

  @GetMapping("{id}")
  public ResponseEntity<Tags> getById(@PathVariable("id") Integer id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }
}
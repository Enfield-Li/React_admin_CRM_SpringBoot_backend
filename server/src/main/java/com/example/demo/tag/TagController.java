package com.example.demo.tag;

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

@RestController
@Tag(name = "Tag")
@RequestMapping("/tags")
class TagController {

  private final TagRepository tagRepo;

  @Autowired
  public TagController(TagRepository tagRepo) {
    this.tagRepo = tagRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAllTag(@RequestBody List<com.example.demo.tag.Tags> tags) {
    tagRepo.saveAll(tags);
  }

  @GetMapping
  public ResponseEntity<List<Tag>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "q", required = false) String query
  ) {
    System.out.println(start);
    System.out.println(end);
    System.out.println(order);
    System.out.println(sort);

    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Tag> getById(@PathVariable("id") Integer id) {
    return null;
  }
}

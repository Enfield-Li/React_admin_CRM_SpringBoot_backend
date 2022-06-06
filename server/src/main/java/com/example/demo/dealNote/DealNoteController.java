package com.example.demo.dealNote;

import com.example.demo.dealNote.entity.DealNote;
import com.example.demo.dealNote.repository.DealNoteRepository;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Deal_Note")
@RequestMapping("/dealnotes")
class DealNoteController {

  private final DealNoteRepository dealNoteRepo;

  @Autowired
  public DealNoteController(DealNoteRepository dealNoteRepo) {
    this.dealNoteRepo = dealNoteRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<DealNote> dealNotes) {
    dealNoteRepo.saveAll(dealNotes);
  }

  @GetMapping
  public ResponseEntity<List<DealNote>> getAll() {
    try {
      List<DealNote> items = new ArrayList<DealNote>();

      dealNoteRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<DealNote> getById(@PathVariable("id") Long id) {
    Optional<DealNote> existingItemOptional = dealNoteRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<DealNote> create(@RequestBody DealNote item) {
    try {
      DealNote savedItem = dealNoteRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<DealNote> update(
    @PathVariable("id") Long id,
    @RequestBody DealNote item
  ) {
    Optional<DealNote> existingItemOptional = dealNoteRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      DealNote existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(
        dealNoteRepo.save(existingItem),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      dealNoteRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

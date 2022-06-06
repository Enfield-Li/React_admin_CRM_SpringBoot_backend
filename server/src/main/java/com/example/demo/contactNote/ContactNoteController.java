package com.example.demo.contactNote;

import com.example.demo.contactNote.entity.ContactNote;
import com.example.demo.contactNote.repository.ContactNoteRepository;
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
@Tag(name = "Contact_Notes")
@RequestMapping("/contactnotes")
class ContactNoteController {

  private final ContactNoteRepository contactNoteRepo;

  @Autowired
  public ContactNoteController(ContactNoteRepository contactNoteRepo) {
    this.contactNoteRepo = contactNoteRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<ContactNote> contactNotes) {
    contactNoteRepo.saveAll(contactNotes);
  }

  @GetMapping
  public ResponseEntity<List<ContactNote>> getAll() {
    try {
      List<ContactNote> items = new ArrayList<ContactNote>();

      contactNoteRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<ContactNote> getById(@PathVariable("id") Long id) {
    Optional<ContactNote> existingItemOptional = contactNoteRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<ContactNote> create(@RequestBody ContactNote item) {
    try {
      ContactNote savedItem = contactNoteRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<ContactNote> update(
    @PathVariable("id") Long id,
    @RequestBody ContactNote item
  ) {
    Optional<ContactNote> existingItemOptional = contactNoteRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      ContactNote existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(
        contactNoteRepo.save(existingItem),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      contactNoteRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

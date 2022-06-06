package com.example.demo.contact;

import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.tag.TagRepository;
import com.example.demo.tag.Tags;
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
@Tag(name = "Contact")
@RequestMapping("/contacts")
class ContactController {

  private final ContactRepository contactRepo;
  private final TagRepository tagRepo;

  @Autowired
  public ContactController(
    ContactRepository contactRepo,
    TagRepository tagRepo
  ) {
    this.contactRepo = contactRepo;
    this.tagRepo = tagRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<Contact> contacts) {
    contacts.forEach(
      contact -> {
        List<Integer> tags = contact.getTags();

        List<Tags> tag_list = new ArrayList<>();
        tags.forEach(
          t -> {
            Tags tag = tagRepo.findById(t).orElse(null);
            tag_list.add(tag);
          }
        );
        contact.setTag_list(tag_list);
        contact.setTags(null);

        contactRepo.save(contact);
      }
    );
  }

  @GetMapping
  public ResponseEntity<List<Contact>> getAll() {
    try {
      List<Contact> items = new ArrayList<Contact>();

      contactRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Contact> getById(@PathVariable("id") Long id) {
    Optional<Contact> existingItemOptional = contactRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody Contact item) {
    try {
      Contact savedItem = contactRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Contact> update(
    @PathVariable("id") Long id,
    @RequestBody Contact item
  ) {
    Optional<Contact> existingItemOptional = contactRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      Contact existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(
        contactRepo.save(existingItem),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      contactRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

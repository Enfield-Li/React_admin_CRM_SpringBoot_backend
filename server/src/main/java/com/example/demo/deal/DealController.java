package com.example.demo.deal;

import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.deal.entity.Deal;
import com.example.demo.deal.repository.DealRespository;
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
@Tag(name = "Deal")
@RequestMapping("/deals")
class DealController {

  private final DealRespository dealRepo;
  private final ContactRepository contactRepo;

  @Autowired
  public DealController(
    DealRespository dealRepo,
    ContactRepository contactRepo
  ) {
    this.dealRepo = dealRepo;
    this.contactRepo = contactRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<Deal> deals) {
    deals.forEach(
      deal -> {
        List<Contact> contact_list = new ArrayList<>();

        deal
          .getContact_ids()
          .forEach(
            id -> {
              Contact contact = contactRepo.findById(id).orElse(null);
              contact_list.add(contact);
            }
          );

        deal.setContact_list(contact_list);
        dealRepo.save(deal);
      }
    );
  }

  @GetMapping
  public ResponseEntity<List<Deal>> getAll() {
    try {
      List<Deal> items = new ArrayList<Deal>();

      dealRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Deal> getById(@PathVariable("id") Long id) {
    Optional<Deal> existingItemOptional = dealRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Deal> create(@RequestBody Deal item) {
    try {
      Deal savedItem = dealRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Deal> update(
    @PathVariable("id") Long id,
    @RequestBody Deal item
  ) {
    Optional<Deal> existingItemOptional = dealRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      Deal existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(dealRepo.save(existingItem), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      dealRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

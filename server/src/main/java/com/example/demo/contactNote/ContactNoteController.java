package com.example.demo.contactNote;

import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.contactNote.entity.ContactNote;
import com.example.demo.contactNote.repository.ContactNoteRepository;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
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
@Tag(name = "Contact_Notes")
@RequestMapping("/contactNotes")
class ContactNoteController {

  private final ContactNoteRepository contactNoteRepo;
  private final EntityManager entityManager;

  @Autowired
  public ContactNoteController(
    ContactNoteRepository contactNoteRepo,
    EntityManager entityManager
  ) {
    this.contactNoteRepo = contactNoteRepo;
    this.entityManager = entityManager;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<ContactNote> contactNotes) {
    contactNotes.forEach(
      contactNote -> {
        Sale sale = entityManager.getReference(
          Sale.class,
          contactNote.getSales_id()
        );
        Contact contact = entityManager.getReference(
          Contact.class,
          contactNote.getContact_id()
        );

        contactNote.setSale(sale);
        contactNote.setContact(contact);
      }
    );

    contactNoteRepo.saveAll(contactNotes);
  }

  @GetMapping
  public ResponseEntity<List<ContactNote>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "sales_id", required = false) String sales_id,
    @RequestParam(name = "q", required = false) String query
  ) {
    System.out.println(start);
    System.out.println(end);
    System.out.println(order);
    System.out.println(sort);

    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<ContactNote> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<ContactNote> create(@RequestBody ContactNote item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<ContactNote> update(
    @PathVariable("id") Long id,
    @RequestBody ContactNote item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

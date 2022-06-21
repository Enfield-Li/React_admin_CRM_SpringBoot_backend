package com.example.demo.contactNote;

import com.example.demo.config.exception.ItemNotFoundException;
import com.example.demo.contact.entity.Contact;
import com.example.demo.contactNote.entity.ContactNote;
import com.example.demo.contactNote.repository.ContactNoteMapper;
import com.example.demo.contactNote.repository.ContactNoteRepository;
import com.example.demo.sale.entity.Sale;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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
  private final ContactNoteMapper contactNoteMapper;

  public ContactNoteController(
    ContactNoteRepository contactNoteRepo,
    EntityManager entityManager,
    ContactNoteMapper contactNoteMapper
  ) {
    this.contactNoteRepo = contactNoteRepo;
    this.entityManager = entityManager;
    this.contactNoteMapper = contactNoteMapper;
  }

  @GetMapping("test")
  public void test() {
    ContactNote cn = contactNoteRepo.findById(1L).orElse(null);
    System.out.println(cn.getDate());
  }

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
    @RequestParam(name = "contact_id", required = false) String contact_id,
    @RequestParam(name = "q", required = false) String query
  ) {
    Integer take = end - start;

    List<ContactNote> contacts = contactNoteMapper.getAllContactNotes(
      start,
      take,
      sort,
      order,
      sales_id,
      contact_id
    );

    String contactNoteCount = contactNoteMapper.getContactNoteCount(
      sales_id,
      contact_id
    );

    return ResponseEntity
      .ok()
      .header("X-Total-Count", contactNoteCount)
      .body(contacts);
  }

  @GetMapping("{id}")
  public ResponseEntity<ContactNote> getById(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PostMapping
  public ResponseEntity<ContactNote> create(@RequestBody ContactNote item) {
    Sale sale = entityManager.getReference(Sale.class, item.getSales_id());
    Contact contact = entityManager.getReference(
      Contact.class,
      item.getContact_id()
    );

    item.setSale(sale);
    item.setContact(contact);

    ContactNote saved = contactNoteRepo.save(item);

    return ResponseEntity.ok().body(saved);
  }

  @Transactional
  @PutMapping("{id}")
  public ResponseEntity<ContactNote> update(
    @PathVariable("id") Long id,
    @RequestBody ContactNote contactNote,
    HttpSession session
  ) {
    System.out.println("saleId: " + session.getAttribute("saleId"));
    ContactNote updatedContactNote = contactNoteRepo
      .findById(id)
      .orElseThrow(() -> new ItemNotFoundException("Contact note", id));

    updatedContactNote.setText(contactNote.getText());

    return ResponseEntity.ok().body(updatedContactNote);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    contactNoteRepo.deleteById(id);

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}

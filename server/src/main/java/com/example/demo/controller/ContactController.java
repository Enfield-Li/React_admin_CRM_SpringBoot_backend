package com.example.demo.controller;

import static com.example.demo.util.Constants.*;

import com.example.demo.dto.UpdateContactTagDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.ContactMapper;
import com.example.demo.repository.ContactRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@Tag(name = "Contact")
@RequiredArgsConstructor
@RequestMapping(CONTACTS_ENDPOINT)
class ContactController {

  private final ContactRepository contactRepo;
  private final EntityManager entityManager;
  private final ContactMapper contactMapper;

  @GetMapping("test")
  public Boolean test(HttpSession session) {
    System.out.println(session.getAttribute("saleId"));
    return true;
  }

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Contact> contacts) {
    contacts.forEach(contact -> setRelationship(contact));
    contactRepo.saveAll(contacts);
  }

  @GetMapping
  public ResponseEntity<List<Contact>> getAll(
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "q", required = false) String query,
    @RequestParam(name = "tags", required = false) String tags,
    @RequestParam(name = "status", required = false) String status,
    @RequestParam(name = "sales_id", required = false) Long sales_id,
    @RequestParam(name = "company_id", required = false) Long company_id,
    @RequestParam(name = "last_seen_gte", required = false) String last_seen_gte,
    @RequestParam(name = "last_seen_lte", required = false) String last_seen_lte
  ) {
    Integer take = end - start;

    List<Contact> companyContacts = contactMapper
        .getCompanyContacts(start, take, sort, order, status, tags, sales_id, last_seen_gte, last_seen_lte, company_id, query);

    String conctactCount = contactMapper
        .getContactCount(status, tags, sales_id, company_id, last_seen_gte, last_seen_lte, query);

    return ResponseEntity
      .ok()
      .header("X-Total-Count", conctactCount)
      .body(companyContacts);
  }

  @GetMapping("{id}")
  public ResponseEntity<Contact> getById(@PathVariable("id") Long id) {
    Contact contact = contactMapper.getContactById(id);

    if (contact == null) {
      throw new ItemNotFoundException("Contact", id);
    }

    return ResponseEntity.ok(processContact(contact));
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Contact>> getManyReference(
    @RequestParam("id") List<Long> ids
  ) {
    List<Contact> contacts = contactMapper.getContactsByIds(ids);

    return ResponseEntity.ok(contacts);
  }

  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody Contact contact) {
    Contact savedContact = contactRepo.save(setRelationship(contact));
    return ResponseEntity.ok(savedContact);
  }

  @Transactional
  @PutMapping("{id}")
  public ResponseEntity<Boolean> update(
    @PathVariable("id") Long id,
    @RequestBody UpdateContactTagDto dto
  ) {
    System.out.println(id);
    System.out.println(dto);

    Contact contact = contactRepo
      .findById(id)
      .orElseThrow(() -> new ItemNotFoundException("Contact", id));

    Set<Tags> tag_list = new HashSet<>();

    dto.getTags().forEach(tagId -> tag_list.add(findTagsReference(tagId)));

    contact.setTag_list(tag_list);

    return ResponseEntity.ok(true);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    contactRepo.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private Contact processContact(Contact contact) {
    String raw_tags = contact.getRaw_tags();

    if (raw_tags != null) {
      ArrayList<String> tagsStr = new ArrayList<String>(
        Arrays.asList(raw_tags.split(","))
      );

      List<Integer> tags = new ArrayList<>();

      tagsStr.forEach(tag -> tags.add(Integer.parseInt(tag)));

      contact.setRaw_tags(null);
      contact.setTags(tags);
    }

    return contact;
  }

  private Contact setRelationship(Contact contact) {
    Sale sale = entityManager.getReference(Sale.class, contact.getSales_id());
    Company company = entityManager
        .getReference(Company.class, contact.getCompany_id());

    contact.setSale(sale);
    contact.setCompany(company);
    contact.setTag_list(
      contact
        .getTags()
        .stream()
        .map(id -> findTagsReference(id))
        .collect(Collectors.toSet())
    );

    return contact;
  }

  private Tags findTagsReference(Integer id) {
    return entityManager.getReference(Tags.class, id);
  }
}

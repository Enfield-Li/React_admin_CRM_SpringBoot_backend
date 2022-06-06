package com.example.demo.contact;

import com.example.demo.company.entity.Company;
import com.example.demo.company.repository.CompanyRepository;
import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleRepository;
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
  private final CompanyRepository companyRepo;
  private final SaleRepository saleRepo;

  @Autowired
  public ContactController(
    ContactRepository contactRepo,
    TagRepository tagRepo,
    CompanyRepository companyRepo,
    SaleRepository saleRepo
  ) {
    this.contactRepo = contactRepo;
    this.tagRepo = tagRepo;
    this.companyRepo = companyRepo;
    this.saleRepo = saleRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Contact> contacts) {
    contacts.forEach(
      contact -> {
        Sale sale = saleRepo.findById(contact.getSales_id()).orElse(null);
        Company company = companyRepo
          .findById(contact.getCompany_id())
          .orElse(null);

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
        contact.setCompany(company);
        contact.setSale(sale);

        contactRepo.save(contact);
      }
    );
  }

  @GetMapping
  public ResponseEntity<List<Contact>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Contact> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody Contact item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<Contact> update(
    @PathVariable("id") Long id,
    @RequestBody Contact item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

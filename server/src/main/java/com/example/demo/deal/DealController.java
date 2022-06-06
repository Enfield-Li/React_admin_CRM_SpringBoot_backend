package com.example.demo.deal;

import com.example.demo.company.entity.Company;
import com.example.demo.company.repository.CompanyRepository;
import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.deal.entity.Deal;
import com.example.demo.deal.repository.DealRespository;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleRepository;
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
  private final CompanyRepository companyRepo;
  private final SaleRepository saleRepo;

  @Autowired
  public DealController(
    DealRespository dealRepo,
    ContactRepository contactRepo,
    CompanyRepository companyRepo,
    SaleRepository saleRepo
  ) {
    this.dealRepo = dealRepo;
    this.contactRepo = contactRepo;
    this.companyRepo = companyRepo;
    this.saleRepo = saleRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Deal> deals) {
    deals.forEach(
      deal -> {
        Sale sale = saleRepo.findById(deal.getSales_id()).orElse(null);
        Company company = companyRepo
          .findById(deal.getCompany_id())
          .orElse(null);

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
        deal.setSale(sale);
        deal.setCompany(company);

        dealRepo.save(deal);
      }
    );
  }

  @GetMapping
  public ResponseEntity<List<Deal>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Deal> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<Deal> create(@RequestBody Deal item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<Deal> update(
    @PathVariable("id") Long id,
    @RequestBody Deal item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

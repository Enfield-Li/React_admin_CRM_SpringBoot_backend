package com.example.demo.company;

import com.example.demo.company.entity.Company;
import com.example.demo.company.repository.CompanyRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Company")
@RequestMapping("/companies")
class CompanyController {

  private final CompanyRepository companyRepo;
  private final EntityManager entityManager;

  @Autowired
  public CompanyController(
    CompanyRepository companyRepo,
    EntityManager entityManager
  ) {
    this.companyRepo = companyRepo;
    this.entityManager = entityManager;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Company> companies) {
    companies.forEach(
      company -> {
        Sale sale = entityManager.getReference(
          Sale.class,
          company.getSales_id()
        );
        company.setSale(sale);
      }
    );

    companyRepo.saveAll(companies);
  }

  @GetMapping
  public ResponseEntity<List<Company>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Company> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<Company> create(@RequestBody Company item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<Company> update(
    @PathVariable("id") Long id,
    @RequestBody Company item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

package com.example.demo.company;

import com.example.demo.company.entity.Company;
import com.example.demo.company.repository.CompanyRepository;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Company")
@RequestMapping("/companies")
class CompanyController {

  private final CompanyRepository companyRepo;
  private final SaleRepository saleRepo;

  @Autowired
  public CompanyController(
    CompanyRepository companyRepo,
    SaleRepository saleRepo
  ) {
    this.companyRepo = companyRepo;
    this.saleRepo = saleRepo;
  }

  @PostMapping("test")
  public void test(@RequestBody Company company) {
    Sale sale = saleRepo.findById(company.getSales_id()).orElse(null);
    company.setSale(sale);
    companyRepo.save(company);
  }

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<Company> company) {
    company.forEach(
      c -> {
        Sale sales = saleRepo.findById(c.getSales_id()).orElse(null);
        c.setSale(sales);
      }
    );

    companyRepo.saveAll(company);
  }

  @GetMapping
  public ResponseEntity<List<Company>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Company> getById(@PathVariable("id") Long id) {
    Optional<Company> existingItemOptional = companyRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Company> create(@RequestBody Company item) {
    try {
      Company savedItem = companyRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Company> update(
    @PathVariable("id") Long id,
    @RequestBody Company item
  ) {
    Optional<Company> existingItemOptional = companyRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      Company existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(
        companyRepo.save(existingItem),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      companyRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

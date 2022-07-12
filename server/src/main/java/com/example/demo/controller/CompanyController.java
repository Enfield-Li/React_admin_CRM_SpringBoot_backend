package com.example.demo.controller;

import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.companyMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
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
@Tag(name = "Company")
@RequestMapping("/companies")
class CompanyController {

  private final CompanyRepository companyRepo;
  private final EntityManager entityManager;
  private final companyMapper companyMapper;

  public CompanyController(
    CompanyRepository companyRepo,
    EntityManager entityManager,
    companyMapper companyMapper
  ) {
    this.companyRepo = companyRepo;
    this.entityManager = entityManager;
    this.companyMapper = companyMapper;
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
  public ResponseEntity<List<Company>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "sales_id", required = false) Long sales_id,
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "sector", required = false) String sector,
    @RequestParam(name = "q", required = false) String query
  ) {
    Integer take = end - start;

    Integer sizeMin = null;
    Integer sizeMax = null;

    if (size != null) {
      switch (size) {
        case 1:
          sizeMax = 1;
          break;
        case 10:
          sizeMin = 1;
          sizeMax = 10;
          break;
        case 50:
          sizeMin = 9;
          sizeMax = 50;
          break;
        case 250:
          sizeMin = 49;
          sizeMax = 250;
          break;
        case 500:
          sizeMin = 250;
          break;
        default:
          break;
      }
    }

    List<Company> filteredCompany = companyMapper.getFilteredCompany(
      start,
      take,
      sort,
      query,
      order,
      sales_id,
      sizeMin,
      sizeMax,
      sector,
      query
    );

    String companyCount = companyMapper.getCompanyCount(
      query,
      sales_id,
      sizeMin,
      sizeMax,
      sector,
      query
    );

    return ResponseEntity
      .ok()
      .header("X-Total-Count", companyCount)
      .body(filteredCompany);
  }

  @GetMapping("{id}")
  public ResponseEntity<Company> getById(@PathVariable("id") Long id) {
    Company company = companyRepo
      .findById(id)
      .orElseThrow(() -> new ItemNotFoundException("Company", id));

    return ResponseEntity.ok().body(company);
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Company>> getManyReference(
    @RequestParam("id") List<Long> ids
  ) {
    List<Company> products = companyMapper.getManyReferences(ids);

    return ResponseEntity.ok().body(products);
  }

  @PostMapping
  public ResponseEntity<Company> create(@RequestBody Company company) {
    // Sale sale = entityManager.getReference(Sale.class, company.getSales_id());
    // company.setSale(sale);

    Company savedCompany = companyRepo.save(company);
    return ResponseEntity.ok().body(savedCompany);
  }

  @PutMapping("{id}")
  public ResponseEntity<Company> update(
    @PathVariable("id") Long id,
    @RequestBody Company item
  ) {
    Company savedCompany = companyRepo.save(item);
    return ResponseEntity.ok().body(savedCompany);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    companyRepo.deleteById(id);
    return ResponseEntity.ok().body(true);
  }
}

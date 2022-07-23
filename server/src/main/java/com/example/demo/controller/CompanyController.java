package com.example.demo.controller;

import static com.example.demo.util.Constants.*;

import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.companyMapper;
import com.example.demo.repository.CompanyRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(COMPANIES_ENDPOINT)
class CompanyController {

  private final EntityManager entityManager;
  private final companyMapper companyMapper;
  private final CompanyRepository companyRepo;

  @PostMapping("test")
  public List<Company> test() {
    return companyRepo.findAll();
  }

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Company> companies) {
    companies.forEach(company -> setRelationship(company));
    companyRepo.saveAll(companies);
  }

  @GetMapping
  public ResponseEntity<List<Company>> getAll(
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "q", required = false) String query,
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "sector", required = false) String sector,
    @RequestParam(name = "sales_id", required = false) Long sales_id
  ) {
    Integer take = end - start;

    Integer minSize = null;
    Integer maxSize = null;

    if (size != null) {
      switch (size) {
        case 1: { maxSize = 1; break; }
        case 10: { minSize = 1; maxSize = 10; break; }
        case 50: { minSize = 10; maxSize = 50; break; }
        case 250: { minSize = 50; maxSize = 250; break; }
        case 500: { minSize = 250; break; }
        default: { break; }
      }
    }

    List<Company> filteredCompany = companyMapper
        .getFilteredCompanies(start, take, sort, order, sales_id, minSize, maxSize, sector, query);

    String companyCount = companyMapper
        .getCompaniesCount(query, sales_id, minSize, maxSize, sector);

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

    return ResponseEntity.ok(company);
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Company>> getManyReference(
    @RequestParam("id") List<Long> ids
  ) {
    List<Company> products = companyMapper.getManyReferences(ids);
    return ResponseEntity.ok(products);
  }

  @PostMapping
  public ResponseEntity<Company> create(@RequestBody Company company) {
    Company savedCompany = companyRepo.save(company);
    return ResponseEntity.ok(savedCompany);
  }

  @PutMapping("{id}")
  public ResponseEntity<Company> update(
    @PathVariable("id") Long id,
    @RequestBody Company item
  ) {
    Company savedCompany = companyRepo.save(item);
    return ResponseEntity.ok(savedCompany);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    companyRepo.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private Company setRelationship(Company company) {
    Sale sale = entityManager.getReference(Sale.class, company.getSales_id());
    company.setSale(sale);
    return company;
  }
}

package com.example.demo.sale;

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
@Tag(name = "Sale")
@RequestMapping("/sales")
class SaleController {

  private final SaleRepository saleRepo;

  @Autowired
  public SaleController(SaleRepository saleRepo) {
    this.saleRepo = saleRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<Sale> sales) {
    saleRepo.saveAll(sales);
  }

  @GetMapping
  public ResponseEntity<List<Sale>> getAll() {
    try {
      List<Sale> items = new ArrayList<Sale>();

      saleRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Sale> getById(@PathVariable("id") Long id) {
    Optional<Sale> existingItemOptional = saleRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Sale> create(@RequestBody Sale item) {
    try {
      Sale savedItem = saleRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Sale> update(
    @PathVariable("id") Long id,
    @RequestBody Sale item
  ) {
    Optional<Sale> existingItemOptional = saleRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      Sale existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(saleRepo.save(existingItem), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      saleRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

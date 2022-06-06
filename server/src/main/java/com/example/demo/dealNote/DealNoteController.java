package com.example.demo.dealNote;

import com.example.demo.deal.entity.Deal;
import com.example.demo.deal.repository.DealRespository;
import com.example.demo.dealNote.entity.DealNote;
import com.example.demo.dealNote.repository.DealNoteRepository;
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
@Tag(name = "Deal_Note")
@RequestMapping("/dealnotes")
class DealNoteController {

  private final DealNoteRepository dealNoteRepo;
  private final SaleRepository saleRepo;
  private final DealRespository dealRepo;

  @Autowired
  public DealNoteController(
    DealNoteRepository dealNoteRepo,
    SaleRepository saleRepo,
    DealRespository dealRepo
  ) {
    this.dealNoteRepo = dealNoteRepo;
    this.saleRepo = saleRepo;
    this.dealRepo = dealRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<DealNote> dealNotes) {
    dealNotes.forEach(
      dn -> {
        Deal deal = dealRepo.findById(dn.getDeal_id()).orElse(null);
        Sale sale = saleRepo.findById(dn.getSales_id()).orElse(null);

        dn.setDeal(deal);
        dn.setSale(sale);
      }
    );

    dealNoteRepo.saveAll(dealNotes);
  }

  @GetMapping
  public ResponseEntity<List<DealNote>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<DealNote> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<DealNote> create(@RequestBody DealNote item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<DealNote> update(
    @PathVariable("id") Long id,
    @RequestBody DealNote item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

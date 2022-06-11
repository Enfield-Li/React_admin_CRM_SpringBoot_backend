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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Deal_Note")
@RequestMapping("/dealNotes")
class DealNoteController {

  private final DealNoteRepository dealNoteRepo;
  private final EntityManager entityManager;

  @Autowired
  public DealNoteController(
    DealNoteRepository dealNoteRepo,
    EntityManager entityManager
  ) {
    this.dealNoteRepo = dealNoteRepo;
    this.entityManager = entityManager;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<DealNote> dealNotes) {
    dealNotes.forEach(
      dealNote -> {
        Sale sale = entityManager.getReference(
          Sale.class,
          dealNote.getSales_id()
        );
        Deal deal = entityManager.getReference(
          Deal.class,
          dealNote.getDeal_id()
        );

        dealNote.setSale(sale);
        dealNote.setDeal(deal);
      }
    );

    dealNoteRepo.saveAll(dealNotes);
  }

  @GetMapping
  public ResponseEntity<List<DealNote>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "q", required = false) String query
  ) {
    System.out.println(start);
    System.out.println(end);
    System.out.println(order);
    System.out.println(sort);

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

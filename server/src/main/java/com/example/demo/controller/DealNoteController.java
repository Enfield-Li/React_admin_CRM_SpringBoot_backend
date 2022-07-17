package com.example.demo.controller;

import com.example.demo.entity.Deal;
import com.example.demo.entity.DealNote;
import com.example.demo.entity.Sale;
import com.example.demo.mapper.DealNoteMapper;
import com.example.demo.repository.DealNoteRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.example.demo.util.Constants.*;

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
@Tag(name = "Deal_Note")
@RequestMapping(DEALNOTES_ENDPOINT)
class DealNoteController {

  private final DealNoteRepository dealNoteRepo;
  private final EntityManager entityManager;
  private final DealNoteMapper dealNoteMapper;

  public DealNoteController(
    DealNoteRepository dealNoteRepo,
    EntityManager entityManager,
    DealNoteMapper dealNoteMapper
  ) {
    this.dealNoteRepo = dealNoteRepo;
    this.entityManager = entityManager;
    this.dealNoteMapper = dealNoteMapper;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<DealNote> dealNotes) {
    dealNotes.forEach(dealNote -> setRelationship(dealNote));
    dealNoteRepo.saveAll(dealNotes);
  }

  @GetMapping
  public ResponseEntity<List<DealNote>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "deal_id", required = false) Long deal_id,
    @RequestParam(name = "q", required = false) String query
  ) {
    Integer take = end - start;

    List<DealNote> dealNotes = dealNoteMapper.getNotesByDealId(
      start,
      take,
      sort,
      order,
      deal_id
    );
    String dealNoteCount = dealNoteMapper.getDealNoteCount(deal_id);

    return ResponseEntity
      .ok()
      .header("X-Total-Count", dealNoteCount)
      .body(dealNotes);
  }

  @GetMapping("{id}")
  public ResponseEntity<DealNote> getById(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PostMapping
  public ResponseEntity<DealNote> create(@RequestBody DealNote dealNote) {
    DealNote createdNote = dealNoteRepo.save(setRelationship(dealNote));
    return ResponseEntity.ok(createdNote);
  }

  @PutMapping("{id}")
  public ResponseEntity<DealNote> update(
    @PathVariable("id") Long id,
    @RequestBody DealNote item
  ) {
    DealNote updatednote = dealNoteRepo.save(item);
    return ResponseEntity.ok(updatednote);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    dealNoteRepo.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private DealNote setRelationship(DealNote dealNote) {
    Sale sale = entityManager.getReference(Sale.class, dealNote.getSales_id());
    Deal deal = entityManager.getReference(Deal.class, dealNote.getDeal_id());

    dealNote.setSale(sale);
    dealNote.setDeal(deal);
    return dealNote;
  }
}

package com.example.demo.controller;

import com.example.demo.dto.UpdateDealDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.DealMapper;
import com.example.demo.repository.DealRespository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Tag(name = "Deal")
@RequestMapping("/deals")
class DealController {

  private final DealRespository dealRepo;
  private final EntityManager entityManager;
  private final DealMapper dealMapper;

  public DealController(
    DealRespository dealRepo,
    EntityManager entityManager,
    DealMapper dealMapper
  ) {
    this.dealRepo = dealRepo;
    this.entityManager = entityManager;
    this.dealMapper = dealMapper;
  }

  @PostMapping("test")
  public List<Deal> test() {
    // return dealMapper.getCompanyDeals();
    return null;
  }

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Deal> deals) {
    deals.forEach(deal -> setRelationship(deal));
    dealRepo.saveAll(deals);
  }

  @GetMapping
  public ResponseEntity<List<Deal>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "q", required = false) String query,
    @RequestParam(name = "type", required = false) String type,
    @RequestParam(name = "sales_id", required = false) Long sales_id,
    @RequestParam(name = "company_id", required = false) Long company_id,
    @RequestParam(name = "stage_neq", required = false) String stage
  ) {
    Integer take = end - start;
    if (sort.equals("index")) sort = "_index";
    if (sort.equals("last_seen")) sort = "start_at";

    List<Deal> companyDeals = dealMapper.getCompanyDeals(
      start,
      take,
      sort,
      order,
      type,
      query,
      sales_id,
      company_id,
      stage
    );

    String dealCount = dealMapper.getDealCount(
      type,
      query,
      sales_id,
      company_id,
      stage
    );

    return ResponseEntity
      .ok()
      .header("X-Total-Count", dealCount)
      .body(processDeal(companyDeals));
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Deal>> getDealList(
    @RequestParam("id") List<Long> ids
  ) {
    List<Deal> deals = dealMapper.getDealsReference(ids);

    return ResponseEntity.ok().body(deals);
  }

  @GetMapping("{id}")
  public ResponseEntity<Deal> getById(@PathVariable("id") Long id) {
    Deal deal = dealRepo
      .findById(id)
      .orElseThrow(() -> new ItemNotFoundException("Deal", id));

    return ResponseEntity.ok().body(deal);
  }

  @PostMapping
  public ResponseEntity<Deal> create(@RequestBody Deal item) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> update(
    @PathVariable("id") Long id,
    @RequestBody UpdateDealDto item
  ) {
    Integer updateResult = dealMapper.updateDealStatus(id, item.getStage());

    return ResponseEntity.ok().body(updateResult > 0);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    dealRepo.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private Deal setRelationship(Deal deal) {
    Sale sale = entityManager.getReference(Sale.class, deal.getSales_id());

    Company company = entityManager.getReference(
      Company.class,
      deal.getCompany_id()
    );

    Set<Contact> contact_list = new HashSet<>();
    deal
      .getContact_list()
      .forEach(
        contactId -> {
          Contact contact = entityManager.getReference(
            Contact.class,
            contactId
          );
          contact_list.add(contact);
        }
      );

    deal.setSale(sale);
    deal.setCompany(company);
    deal.setContact_list(contact_list);
    return deal;
  }

  private List<Deal> processDeal(List<Deal> deals) {
    for (Deal deal : deals) {
      String[] ids = deal.getContactIdsString().split(",");
      List<String> idsString = Arrays.asList(ids);

      List<Long> idsLong = new ArrayList<>();
      idsString.forEach(id -> idsLong.add(Long.parseLong(id)));

      deal.setContact_ids(idsLong);
      deal.setContactIdsString(null);
    }

    return deals;
  }
}

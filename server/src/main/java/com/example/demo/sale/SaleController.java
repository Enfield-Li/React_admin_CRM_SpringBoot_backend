package com.example.demo.sale;

import com.example.demo.config.exception.ItemNotFoundException;
import com.example.demo.sale.dto.LoginSaleDto;
import com.example.demo.sale.dto.SaleResponseDto;
import com.example.demo.sale.entity.Password;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleMapper;
import com.example.demo.sale.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@Tag(name = "Sale")
@RequestMapping("/sales")
@CrossOrigin("http://localhost:3000")
class SaleController {

  private final SaleRepository saleRepo;
  private final EntityManager entityManager;
  private final SaleMapper saleMapper;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SaleController(
    SaleRepository saleRepo,
    EntityManager entityManager,
    SaleMapper saleMapper,
    PasswordEncoder passwordEncoder
  ) {
    this.saleRepo = saleRepo;
    this.entityManager = entityManager;
    this.saleMapper = saleMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("test")
  public void test() {}

  @PostMapping("login")
  public ResponseEntity<SaleResponseDto> login(
    @RequestBody LoginSaleDto dto,
    HttpSession session
  ) {
    Optional<Sale> sale = saleRepo.findByFullName(dto.getSaleName());

    if (sale.isPresent()) {
      Sale salePresent = sale.get();

      Boolean matched = salePresent
        .getPassword()
        .matchPassword(dto.getRawPassword(), passwordEncoder);

      if (matched) {
        SaleResponseDto responseDto = new SaleResponseDto();

        responseDto.setId(salePresent.getId());
        responseDto.setFullName(
          salePresent.getFirst_name() + " " + salePresent.getLast_name()
        );

        session.setAttribute("saleId", salePresent.getId());

        return ResponseEntity.ok().body(responseDto);
      }
    }

    return ResponseEntity
      .status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
      .build();
  }

  @PostMapping("logout")
  public ResponseEntity<Boolean> logout(HttpSession session) {
    session.removeAttribute("saleId");
    return ResponseEntity.ok().body(true);
  }

  @PostMapping("verify")
  public ResponseEntity<Sale> verify(HttpSession session) {
    Long saleId = (Long) session.getAttribute("saleId");

    Sale sale = saleRepo
      .findById(saleId)
      .orElseThrow(
        () ->
          new ItemNotFoundException("Sales with id: " + saleId + " not found")
      );

    return ResponseEntity.ok().body(sale);
  }

  @GetMapping
  public ResponseEntity<List<Sale>> getAll(
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_sort") String sort
  ) {
    Integer take = end - start;

    List<Sale> sales = saleMapper.getAllSales(start, take, sort, order);
    String saleCount = saleMapper.getSaleCount();

    return ResponseEntity.ok().header("X-Total-Count", saleCount).body(sales);
  }

  @GetMapping(params = "id")
  public ResponseEntity<List<Sale>> getListById(
    @RequestParam(name = "id") Long id
  ) {
    Sale sales = saleRepo
      .findById(id)
      .orElseThrow(
        () -> new ItemNotFoundException("Sales with id: " + id + " not found")
      );

    return ResponseEntity.ok().body(Arrays.asList(sales));
  }

  @GetMapping("{id}")
  public ResponseEntity<Sale> getById(@PathVariable("id") Long id) {
    System.out.println(id);
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PostMapping
  public ResponseEntity<Sale> create(@RequestBody Sale item) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PutMapping("{id}")
  public ResponseEntity<Sale> update(
    @PathVariable("id") Long id,
    @RequestBody Sale item
  ) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Sale> sales) {
    saleRepo.saveAll(sales);
  }

  @Transactional
  @GetMapping("setPassword")
  public void setPassword() {
    List<Sale> sales = saleRepo.findAll();

    sales.forEach(
      sale -> {
        String fullName = sale.getFirst_name() + " " + sale.getLast_name();
        Password password = Password.encode(fullName, passwordEncoder);
        sale.setPassword(password);
      }
    );
  }
}

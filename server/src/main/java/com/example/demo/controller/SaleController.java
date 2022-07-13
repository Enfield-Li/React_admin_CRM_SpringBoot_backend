package com.example.demo.controller;

import com.example.demo.dto.LoginSaleDto;
import com.example.demo.dto.SaleRegisterResponseDto;
import com.example.demo.dto.SaleResponseDto;
import com.example.demo.dto.UpdateSaleDto;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.SaleMapper;
import com.example.demo.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
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
class SaleController {

  private static final String PENDING = "pending";
  private static final String FORMAL = "formal";

  private final SaleRepository saleRepo;
  private final SaleMapper saleMapper;
  private final PasswordEncoder passwordEncoder;

  public SaleController(
    SaleRepository saleRepo,
    SaleMapper saleMapper,
    PasswordEncoder passwordEncoder
  ) {
    this.saleRepo = saleRepo;
    this.saleMapper = saleMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("test")
  public String test(@Valid @RequestBody LoginSaleDto dto) {
    return "Have access";
  }

  @PostMapping("login")
  public void login(@RequestBody LoginSaleDto dto) {}

  @PostMapping("register")
  public SaleRegisterResponseDto register(
    @Valid @RequestBody LoginSaleDto dto
  ) {
    try {
      String firstName = null;
      String lastName = null;

      String username = dto.getUsername();
      boolean isFullName = username.contains(" ");

      if (isFullName) {
        String[] usernameArr = username.split(" ");
        firstName = usernameArr[0];
        lastName = usernameArr[1];
      } else {
        firstName = username;
      }

      Sale newSale = new Sale();
      newSale.setStatus(PENDING);
      newSale.setFirst_name(firstName);
      newSale.setLast_name(lastName);
      newSale.setPassword(passwordEncoder.encode(dto.getPassword()));

      Sale savedSale = saleRepo.save(newSale);

      return new SaleRegisterResponseDto(savedSale, null);
    } catch (ConstraintViolationException err) {
      return new SaleRegisterResponseDto(null, "Username already exist.");
    }
  }

  @PostMapping("logout")
  public void logout() {}

  @GetMapping("me")
  public Authentication me() {
    return SecurityContextHolder.getContext().getAuthentication();
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
      .orElseThrow(() -> new ItemNotFoundException("Sales", id));

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

  @Transactional
  @PutMapping("update_role")
  public ResponseEntity<Sale> update(@RequestBody UpdateSaleDto dto) {
    Optional<Sale> sale = null;

    if (dto.getId() != null) {
      sale = saleRepo.findById(dto.getId());
    } else if (dto.getUsername() != null) {
      sale = saleRepo.findByFullName(dto.getUsername());
    }

    sale.orElseThrow(
      () -> new ItemNotFoundException("Sales person", dto.getUsername())
    );

    sale.get().setRole(dto.getRole());
    sale.get().setStatus(FORMAL);

    return ResponseEntity.ok().body(sale.get());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    saleRepo.deleteById(id);
    return ResponseEntity.ok().body(null);
  }

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Sale> sales) {
    saleRepo.saveAll(sales);
  }

  @Transactional
  @GetMapping("set_password")
  public void setPassword() {
    List<Sale> sales = saleRepo.findAll();

    sales.forEach(
      sale -> {
        String fullName = sale.getFirst_name() + " " + sale.getLast_name();
        sale.setPassword(passwordEncoder.encode(fullName));
      }
    );
  }
}

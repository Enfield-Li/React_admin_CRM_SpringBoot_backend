package com.example.demo.controller;

import static com.example.demo.util.Constants.*;

import com.example.demo.dto.LoginSaleDto;
import com.example.demo.dto.RegisterSaleDto;
import com.example.demo.dto.SaleRegisterResponseDto;
import com.example.demo.dto.UpdateSaleDto;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.mapper.SaleMapper;
import com.example.demo.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
@RequestMapping(SALES_ENDPOINT)
class SaleController {

  private static final String PENDING = "pending";
  private static final String FORMAL = "formal";

  private final SaleMapper saleMapper;
  private final SaleRepository saleRepo;
  private final PasswordEncoder passwordEncoder;

  // @PostMapping(TEST)
  // public String test() {
  //   return "Have access";
  // }

  @PostMapping(LOGIN)
  public void login(@Valid @RequestBody LoginSaleDto dto) {}

  @PostMapping(REGISTER)
  public ResponseEntity<SaleRegisterResponseDto> register(
    @Valid @RequestBody RegisterSaleDto dto,
    HttpSession session
  ) {
    try {
      String username = dto.getUsername();

      String firstName = null;
      String lastName = null;

      boolean isFullName = username.contains(" ");

      if (isFullName) {
        String[] usernameArr = username.split(" ");
        firstName = usernameArr[0];
        lastName = usernameArr[1];
      } else {
        firstName = username;
      }

      String encryptedPassword = passwordEncoder.encode(dto.getPassword());

      Sale newSale = new Sale(
        PENDING,
        firstName,
        lastName,
        encryptedPassword,
        dto.getRole()
      );
      Sale savedSale = saleRepo.save(newSale);

      session.setAttribute(ApplicationUserInSession, savedSale);

      return ResponseEntity.ok(new SaleRegisterResponseDto(savedSale, null));
    } catch (DataIntegrityViolationException err) {
      return ResponseEntity
        .badRequest()
        .body(new SaleRegisterResponseDto(null, "Username already exist."));
    }
  }

  @PostMapping(LOGOUT)
  public void logout() {}

  @GetMapping("me")
  public Authentication me() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @GetMapping
  public ResponseEntity<List<Sale>> getAll(
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_start") Integer start
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

    return ResponseEntity.ok(Arrays.asList(sales));
  }

  @Transactional
  @PutMapping(UPDATE_ROLE)
  public ResponseEntity<Sale> update(@Valid @RequestBody UpdateSaleDto dto) {
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

    return ResponseEntity.ok(sale.get());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    saleRepo.deleteById(id);
    return ResponseEntity.ok().build();
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

package com.example.demo.controller;

import com.example.demo.dto.LoginSaleDto;
import com.example.demo.dto.SaleResponseDto;
import com.example.demo.entity.Sale;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.SaleMapper;
import com.example.demo.repository.SaleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

  @GetMapping("test")
  public void test(HttpSession session) {
    System.out.println(session.getAttribute("saleId"));
    Enumeration<String> attributes = session.getAttributeNames();
    System.out.println("attributes: ");
    while (attributes.hasMoreElements()) {
      String attribute = (String) attributes.nextElement();
      System.out.println(
        attribute + " : " + session.getAttribute(attribute).toString()
      );
    }

    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    String username = auth.getName(); //get logged in username
    System.out.println("username: " + username);
  }

  // @PostMapping("login")
  // public Boolean login(HttpSession session) { // Javonte Mills
  //   System.out.println(session.getAttribute("saleId"));
  //   Enumeration<String> attributes = session.getAttributeNames();
  //   System.out.println("attributes: ");
  //   while (attributes.hasMoreElements()) {
  //     String attribute = (String) attributes.nextElement();
  //     System.out.println(
  //       attribute + " : " + session.getAttribute(attribute).toString()
  //     );
  //   }

  //   Authentication auth = SecurityContextHolder
  //     .getContext()
  //     .getAuthentication();
  //   String username = auth.getName(); //get logged in username
  //   System.out.println("username: " + username);
  //   return true;
  // }

  // Javonte Mills
  @PostMapping("login")
  public void login(@RequestBody LoginSaleDto dto) {}

  @PostMapping("register")
  public void register(@RequestBody LoginSaleDto dto) {}

  @PostMapping("logout")
  public ResponseEntity<Boolean> logout(HttpSession session) {
    session.removeAttribute("saleId");
    return ResponseEntity.ok().body(true);
  }

  @GetMapping("me")
  public void me() {
    // System.out.println(session.getId());
    // System.out.println(session.getAttribute("saleId"));
    // Long id = (Long) session.getAttribute("saleId");

    // if (id == null || saleId != id) {
    //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    // }

    // Sale sale = saleRepo
    //   .findById(id)
    //   .orElseThrow(() -> new ItemNotFoundException("Sales", id));

    // SaleResponseDto saleResponse = new SaleResponseDto();

    // saleResponse.setId(sale.getId());
    // saleResponse.setFullName(sale.getFirst_name() + " " + sale.getLast_name());
    // saleResponse.setAvatar(
    //   "https://robohash.org/" + saleResponse.getFullName() + ".png"
    // );

    // return ResponseEntity.ok().body(saleResponse);
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
        sale.setPassword(passwordEncoder.encode(fullName));
      }
    );
  }
}

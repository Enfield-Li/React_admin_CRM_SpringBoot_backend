package com.example.demo;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) throws IOException {
    ConfigurableApplicationContext ctx = SpringApplication.run(
      DemoApplication.class,
      args
    );
    // openSwaggerUI();

    SaleRepository saleRepo = ctx.getBean(SaleRepository.class);

    Sale sale = new Sale();
    sale.setId(1L);
    sale.setPassword("password");
    sale.setLast_name("last_name");
    sale.setFirst_name("first_name");
    sale.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company1 = new Company();
    company1.setSale(sale);
    company1.setSize(10);
    company1.setName("company1");
    company1.setCity("guangzhou");
    company1.setSector("consumer");
    company1.setStateAbbr("guangdong");

    Company company2 = new Company();
    company2.setSale(sale);
    company2.setSize(20);
    company2.setName("company2");
    company2.setCity("guangzhou");
    company2.setSector("consumer");
    company2.setStateAbbr("guangdong");

    sale.setCompanies(Set.of(company1, company2));
    // sale.setCompanies(Set.of(company1, company2));
    Sale savedSales = saleRepo.save(sale);
  }

  private static void openSwaggerUI() throws IOException {
    Runtime rt = Runtime.getRuntime();
    rt.exec(
      "rundll32 url.dll,FileProtocolHandler " +
      "http://localhost:3080/swagger-ui/index.html"
    );
  }
}

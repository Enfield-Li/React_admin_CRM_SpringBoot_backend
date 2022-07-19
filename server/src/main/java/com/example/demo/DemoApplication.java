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

    Sale sale1 = new Sale();
    sale1.setPassword("password");
    sale1.setLast_name("last_name1");
    sale1.setFirst_name("first_name1");
    sale1.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company1 = new Company();
    company1.setSale(sale1);
    company1.setSize(10);
    company1.setName("company1");
    company1.setCity("guangzhou");
    company1.setSector("consumer");
    company1.setStateAbbr("guangdong");

    Company company2 = new Company();
    company2.setSale(sale1);
    company2.setSize(20);
    company2.setName("company2");
    company2.setCity("guangzhou");
    company2.setSector("consumer");
    company2.setStateAbbr("guangdong");

    Sale sale2 = new Sale();
    sale2.setPassword("password");
    sale2.setLast_name("last_name2");
    sale2.setFirst_name("first_name2");
    sale2.setRole(ApplicationUserRole.SALE_ADMIN);

    Company company3 = new Company();
    company3.setSale(sale2);
    company3.setSize(10);
    company3.setName("company3");
    company3.setCity("guangzhou");
    company3.setSector("sport");
    company3.setStateAbbr("guangdong");

    Company company4 = new Company();
    company4.setSale(sale2);
    company4.setSize(20);
    company4.setName("company4");
    company4.setCity("guangzhou");
    company4.setSector("sport");
    company4.setStateAbbr("guangdong");

    sale1.setCompanies(Set.of(company1, company2));
    sale2.setCompanies(Set.of(company3, company4));

    saleRepo.saveAll(List.of(sale1, sale2));
  }

  private static void openSwaggerUI() throws IOException {
    Runtime rt = Runtime.getRuntime();
    rt.exec(
      "rundll32 url.dll,FileProtocolHandler " +
      "http://localhost:3080/swagger-ui/index.html"
    );
  }
}

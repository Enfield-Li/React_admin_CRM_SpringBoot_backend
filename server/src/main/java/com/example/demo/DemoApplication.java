package com.example.demo;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Sale;
import com.example.demo.mapper.companyMapper;
import com.example.demo.repository.SaleRepository;
import java.io.IOException;
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
    companyMapper mapper = ctx.getBean(companyMapper.class);

    Sale sale1 = new Sale(
      "first_name1",
      "last_name1",
      "password",
      ApplicationUserRole.SALE_ADMIN
    );

    Company company1 = new Company(
      sale1,
      "company1",
      "guangzhou",
      10,
      "consumer",
      "GD"
    );

    Company company2 = new Company(
      sale1,
      "company2",
      "guangzhou",
      20,
      "consumer",
      "GD"
    );

    Sale sale2 = new Sale(
      "first_name2",
      "last_name2",
      "password",
      ApplicationUserRole.SALE_ADMIN
    );

    Company company3 = new Company(
      sale1,
      "company2",
      "shenzhen",
      10,
      "sport",
      "GD"
    );

    Company company4 = new Company(
      sale2,
      "company4",
      "shenzhen",
      20,
      "sport",
      "GD"
    );

    sale1.setCompanies(Set.of(company1, company2));
    sale2.setCompanies(Set.of(company3, company4));
    // saleRepo.saveAll(List.of(sale1, sale2));

    // List<Company> res1 = mapper.getFilteredCompanies(
    //   0,
    //   100,
    //   "id",
    //   "desc",
    //   1L,
    //   null,
    //   null,
    //   null,
    //   null
    // );

    // System.out.println(res1.size());

    // List<Company> res = mapper.getFilteredCompanies(
    //   0,
    //   100,
    //   "id",
    //   "desc",
    //   2L,
    //   null,
    //   null,
    //   null,
    //   null
    // );

    // System.out.println(res.size());
  }

  private static void openSwaggerUI() throws IOException {
    Runtime rt = Runtime.getRuntime();
    rt.exec(
      "rundll32 url.dll,FileProtocolHandler " +
      "http://localhost:3080/swagger-ui/index.html"
    );
  }
}

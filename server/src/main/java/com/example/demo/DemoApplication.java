package com.example.demo;

import static com.example.demo.util.DateBefore.*;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactNote;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Tags;
import com.example.demo.entity.Task;
import com.example.demo.mapper.companyMapper;
import com.example.demo.repository.ContactNoteRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.DealRespository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.TagsRepository;
import com.example.demo.repository.TaskRepository;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
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
    ContactRepository contactRepo = ctx.getBean(ContactRepository.class);
    TagsRepository tagsRepo = ctx.getBean(TagsRepository.class);
    ContactNoteRepository contactNoteRepo = ctx.getBean(
      ContactNoteRepository.class
    );
    DealRespository dealRepo = ctx.getBean(DealRespository.class);
    TaskRepository taskRepo = ctx.getBean(TaskRepository.class);
    // Sale sale1 = new Sale(
    //   "first_name1",
    //   "last_name1",
    //   "password",
    //   ApplicationUserRole.SALE_ADMIN
    // );

    // Sale sale2 = new Sale(
    //   "first_name2",
    //   "last_name2",
    //   "password",
    //   ApplicationUserRole.SALE_ADMIN
    // );

    // Company company1 = new Company(
    //   sale1,
    //   "company1",
    //   "guangzhou",
    //   10,
    //   "consumer",
    //   "GD"
    // );

    // Company company2 = new Company(
    //   sale1,
    //   "company2",
    //   "guangzhou",
    //   20,
    //   "consumer",
    //   "GD"
    // );

    // sale1.setCompanies(Set.of(company1));
    // sale2.setCompanies(Set.of(company2));

    // Contact contact1 = new Contact(
    //   "contact1_FN",
    //   "contact1_LN",
    //   "title1",
    //   "status1",
    //   daysBefore(5),
    //   company1,
    //   sale1,
    //   "background1"
    // );

    // Contact contact2 = new Contact(
    //   "contact2_FN",
    //   "contact2_LN",
    //   "title2",
    //   "status2",
    //   daysBefore(10),
    //   company2,
    //   sale2,
    //   "background2"
    // );

    // Tags tag1 = new Tags("tag1", "color1");
    // Tags tag2 = new Tags("tag2", "color2");

    // tag1.addContact(contact1);
    // tag2.addContact(contact2);

    // contact1.addTags(tag1);
    // contact2.addTags(tag2);

    // Deal deal1 = new Deal("deal1", "description1" ,"stage1", "type1", 5000L, sale1, company1);
    // Deal deal2 = new Deal("deal2", "description2","stage2", "type2", 10000L, sale2, company2);

    // contact1.addDeals(deal1);
    // contact2.addDeals(deal2);

    // deal1.addContact(contact1);
    // deal2.addContact(contact2);

    // saleRepo.saveAll(List.of(sale1, sale2));
    // tagsRepo.saveAll(List.of(tag1, tag2));
    // contactRepo.saveAll(List.of(contact1, contact2));
    // dealRepo.saveAll(List.of(deal1, deal2));

  }

  private static void openSwaggerUI() throws IOException {
    Runtime rt = Runtime.getRuntime();
    rt.exec(
      "rundll32 url.dll,FileProtocolHandler " +
      "http://localhost:3080/swagger-ui/index.html"
    );
  }
}

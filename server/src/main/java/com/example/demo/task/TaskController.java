package com.example.demo.task;

import com.example.demo.contact.entity.Contact;
import com.example.demo.contact.repository.ContactRepository;
import com.example.demo.sale.entity.Sale;
import com.example.demo.sale.repository.SaleRepository;
import com.example.demo.task.entity.Task;
import com.example.demo.task.repository.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Tasks")
@RequestMapping("/tasks")
class TaskController {

  private final TaskRepository taskRepo;
  private final ContactRepository contactRepo;
  private final SaleRepository saleRepo;

  @Autowired
  public TaskController(
    TaskRepository taskRepo,
    ContactRepository contactRepo,
    SaleRepository saleRepo
  ) {
    this.taskRepo = taskRepo;
    this.contactRepo = contactRepo;
    this.saleRepo = saleRepo;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Task> tasks) {
    tasks.forEach(
      task -> {
        Sale sale = saleRepo.findById(task.getSales_id()).orElse(null);
        Contact contact = contactRepo
          .findById(task.getContact_id())
          .orElse(null);

        task.setSale(sale);
        task.setContact(contact);
      }
    );

    taskRepo.saveAll(tasks);
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<Task> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody Task item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<Task> update(
    @PathVariable("id") Long id,
    @RequestBody Task item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}

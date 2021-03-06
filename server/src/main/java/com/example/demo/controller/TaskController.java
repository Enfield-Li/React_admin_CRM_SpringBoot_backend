package com.example.demo.controller;

import static com.example.demo.util.Constants.*;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Sale;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Tasks")
@RequiredArgsConstructor
@RequestMapping(TASKS_ENDPOINT)
class TaskController {

  private final TaskMapper taskMapper;
  private final TaskRepository taskRepo;
  private final EntityManager entityManager;

  @PostMapping("test")
  public void test() {}

  @PostMapping("bulk_insert")
  public void saveAll(@RequestBody List<Task> tasks) {
    tasks.forEach(task -> setRelationship(task));
    taskRepo.saveAll(tasks);
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAll(
    @RequestParam(name = "_end") Integer end,
    @RequestParam(name = "_sort") String sort,
    @RequestParam(name = "_order") String order,
    @RequestParam(name = "_start") Integer start,
    @RequestParam(name = "contact_id", required = false) Long contact_id
  ) {
    Integer take = end - start;

    List<Task> tasks = taskMapper
        .getAllTasks(start, take, sort, order, contact_id);

    String taskCount = taskMapper.getTaskCount();

    return ResponseEntity.ok().header("X-Total-Count", taskCount).body(tasks);
  }

  @GetMapping("{id}")
  public ResponseEntity<Task> getById(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody Task item) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PutMapping("{id}")
  public ResponseEntity<Task> update(
    @PathVariable("id") Long id,
    @RequestBody Task item
  ) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    taskRepo.deleteById(id);
    return ResponseEntity.ok().body(true);
  }

  private Task setRelationship(Task task) {
    Sale sale = entityManager
        .getReference(Sale.class, task.getSales_id());
    Contact contact = entityManager
        .getReference(Contact.class, task.getContact_id());

    task.setSale(sale);
    task.setContact(contact);

    return task;
  }
}

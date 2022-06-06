package com.example.demo.task;

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

  TaskRepository taskRepo;

  @Autowired
  public TaskController(TaskRepository repository) {
    this.taskRepo = repository;
  }

  @PostMapping("test")
  public void test() {}

  @PostMapping("saveAll")
  public void saveAll(@RequestBody List<Task> tasks) {
    taskRepo.saveAll(tasks);
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAll() {
    try {
      List<Task> items = new ArrayList<Task>();

      taskRepo.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Task> getById(@PathVariable("id") Long id) {
    Optional<Task> existingItemOptional = taskRepo.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody Task item) {
    try {
      Task savedItem = taskRepo.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Task> update(
    @PathVariable("id") Long id,
    @RequestBody Task item
  ) {
    Optional<Task> existingItemOptional = taskRepo.findById(id);
    if (existingItemOptional.isPresent()) {
      Task existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(taskRepo.save(existingItem), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      taskRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}

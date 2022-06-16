package com.example.demo.student;

import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("api/v1/students")
class StudentController {

  private static final List<Student> allUsers = Arrays.asList(
    new Student(1L, "student1"),
    new Student(2L, "student2"),
    new Student(3L, "student3")
  );

  @GetMapping("{id}")
  Student getOneUser(@PathVariable("id") Long id) {
    return allUsers
      .stream()
      .filter(student -> student.getId() == id)
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("User does not exist..."));
  }
}

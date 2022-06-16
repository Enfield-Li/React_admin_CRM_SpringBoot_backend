package com.example.demo.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
class StudentManagementController {

  private static final List<Student> STUDENTS = Arrays.asList(
    new Student(1, "student1"),
    new Student(2, "student2"),
    new Student(3, "student3")
  );

  // hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
  public List<Student> getAllStudents() {
    System.out.println("getAllStudents");
    return STUDENTS;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('student:write')")
  public String registerNewStudent() {
    System.out.println("registerNewStudent");
    return "registerNewStudent";
  }

  @DeleteMapping("{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public String deleteStudent(@PathVariable("studentId") Integer studentId) {
    System.out.println("deleteStudent");
    System.out.println(studentId);
    return "deleteStudent";
  }

  @PutMapping("{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public String updateStudent(@PathVariable("studentId") Integer studentId) {
    System.out.println("updateStudent");
    return "updateStudent";
  }
}

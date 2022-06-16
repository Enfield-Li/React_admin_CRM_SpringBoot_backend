package com.example.demo.user;

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
@RequestMapping("/user")
class UserController {

  private static final List<User> allUsers = Arrays.asList(
    new User(1L, "user1"),
    new User(2L, "user2"),
    new User(3L, "user3")
  );

  @GetMapping("{id}")
  User getOneUser(@PathVariable("id") Long id) {
    return allUsers
      .stream()
      .filter(user -> user.getId() == id)
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("User does not exist..."));
  }
}

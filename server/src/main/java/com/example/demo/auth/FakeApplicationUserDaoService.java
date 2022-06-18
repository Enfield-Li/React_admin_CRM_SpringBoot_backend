package com.example.demo.auth;

import static com.example.demo.security.ApplicationUserRole.*;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<ApplicationUser> selectApplicationUserByUsername(
    String username
  ) {
    return getApplicationUsers()
      .stream()
      .filter(applicationUser -> username.equals(applicationUser.getUsername()))
      .findFirst();
  }

  private List<ApplicationUser> getApplicationUsers() {
    List<ApplicationUser> applicationUsers = Lists.newArrayList(
      new ApplicationUser(
        "student1",
        passwordEncoder.encode("student1"),
        STUDENT.getGrantedAuthorities(),
        true,
        true,
        true,
        true
      ),
      new ApplicationUser(
        "admin1",
        passwordEncoder.encode("admin1"),
        ADMIN.getGrantedAuthorities(),
        true,
        true,
        true,
        true
      ),
      new ApplicationUser(
        "adminTrainee1",
        passwordEncoder.encode("adminTrainee1"),
        ADMINTRAINEE.getGrantedAuthorities(),
        true,
        true,
        true,
        true
      )
    );

    return applicationUsers;
  }
}

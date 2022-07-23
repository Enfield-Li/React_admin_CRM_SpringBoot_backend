package com.example.demo.controller;

import static com.example.demo.util.Constants.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.auth.user.ApplicationUserRole;
import com.example.demo.entity.Sale;
import com.example.demo.mapper.SaleMapper;
import com.example.demo.repository.SaleRepository;
import javax.servlet.http.HttpSession;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class SaleControllerTest implements WithAssertions {

  @Mock
  HttpSession session;

  @Mock
  private SaleRepository saleRepo;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  private SaleMapper mapper;

  private SaleController underTest;

  @BeforeEach
  void setUp() {
    underTest = new SaleController(mapper, saleRepo, passwordEncoder);
  }

  // https://stackoverflow.com/questions/20189116/test-with-httpsession-attributes-set-and-get-is-null
  // https://stackoverflow.com/questions/66961507/how-to-mock-httpsession-and-pass-it-as-arguemnt-to-a-method-which-works-with-ses
  // https://stackoverflow.com/questions/48075652/junit-test-for-servlet-when-use-httpsession
  @Test
  void testRegister() {
    String firstName = "firstName";
    String lastName = "lastName";
    String rawPassword = "rawPassword";

    String encryptedPassword = passwordEncoder.encode(rawPassword);

    verify(passwordEncoder).encode(rawPassword);

    Sale newSale = new Sale(
      "PENDING",
      firstName,
      lastName,
      encryptedPassword,
      ApplicationUserRole.SALE_ADMIN
    );
    saleRepo.save(newSale);

    ArgumentCaptor<Sale> saleArgCaptor = ArgumentCaptor.forClass(Sale.class);

    verify(saleRepo).save(saleArgCaptor.capture());
    Sale capturedStudent = saleArgCaptor.getValue();

    assertThat(capturedStudent).isEqualTo(newSale);

    session.setAttribute(ApplicationUserInSession, newSale);
    // when(session.getAttribute("currentUserUsername")).thenReturn("name");

    verify(session, times(1)).setAttribute(ApplicationUserInSession, newSale);
  }

  @Test
  void testDelete() {}

  @Test
  void testGetAll() {}

  @Test
  void testGetListById() {}

  @Test
  void testLogin() {}

  @Test
  void testLogout() {}

  @Test
  void testMe() {}

  @Test
  void testSaveAll() {}

  @Test
  void testSetPassword() {}

  @Test
  void testUpdate() {}
}

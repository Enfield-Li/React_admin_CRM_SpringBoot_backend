package com.example.demo.auth.filter;

import static com.example.demo.util.Constants.ApplicationUserInSession;

import com.example.demo.auth.user.ApplicationUser;
import com.example.demo.dto.LoginSaleDto;
import com.example.demo.dto.SaleResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  public LoginFilter(String url, AuthenticationManager authManager) {
    super(url);
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest req,
    HttpServletResponse res
  )
    throws AuthenticationException, IOException {
    LoginSaleDto dto = new ObjectMapper()
    .readValue(req.getInputStream(), LoginSaleDto.class);
    System.out.println(dto.toString());

    return getAuthenticationManager()
      .authenticate(
        new UsernamePasswordAuthenticationToken(
          dto.getUsername(),
          dto.getPassword()
        )
      );
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest req,
    HttpServletResponse res,
    FilterChain chain,
    Authentication auth
  )
    throws IOException, ServletException {
    ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

    req.getSession().setAttribute(ApplicationUserInSession, applicationUser);

    res.setContentType("application/json");
    res.getOutputStream().print(SaleResponseDto.toJSON(applicationUser));
  }
}

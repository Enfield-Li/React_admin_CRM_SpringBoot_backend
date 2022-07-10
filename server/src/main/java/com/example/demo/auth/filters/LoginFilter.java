package com.example.demo.auth.filters;

import com.example.demo.auth.users.ApplicationUser;
import com.example.demo.dto.LoginSaleDto;
import com.example.demo.dto.SaleResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  public LoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest req,
    HttpServletResponse res
  )
    throws AuthenticationException, IOException {
    System.out.println("loginFilter attempt auth called...");
    // String username = req.getParameter("username");
    // String password = req.getParameter("password");

    LoginSaleDto creds = new ObjectMapper()
    .readValue(req.getInputStream(), LoginSaleDto.class);
    String username = creds.getUsername();
    String password = creds.getPassword();

    System.out.println("Username in attempt auth: " + username);
    System.out.println("Password in attempt auth: " + password);
    // System.out.println(extractPostRequestBody(httpRequest))

    // System.out.println("req.tostring: " + req.toString());

    // Enumeration<String> reqAttributes = req.getAttributeNames();
    // System.out.println("req attribute: ");
    // while (reqAttributes.hasMoreElements()) {
    //   String attribute = (String) reqAttributes.nextElement();
    //   System.out.println(
    //     attribute + " : " + req.getAttribute(attribute).toString()
    //   );
    // }

    // HttpSession session = req.getSession();
    // Enumeration<String> sessionAttr = session.getAttributeNames();
    // System.out.println("session attributes: ");
    // while (sessionAttr.hasMoreElements()) {
    //   String attribute = (String) sessionAttr.nextElement();
    //   System.out.println(
    //     attribute + " : " + session.getAttribute(attribute).toString()
    //   );
    // }

    return getAuthenticationManager()
      .authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
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
    System.out.println("loginFilter success called...");

    ApplicationUser user = (ApplicationUser) auth.getPrincipal();

    req.getSession().setAttribute("appUser", user);

    res.setContentType("application/json");

    res.getOutputStream().print(SaleResponseDto.toJSON(user));
  }
}

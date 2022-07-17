package com.example.demo.auth.filter;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LogoutFilter extends AbstractAuthenticationProcessingFilter {

  public LogoutFilter(String url) {
    super(url);
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest req,
    HttpServletResponse res
  )
    throws AuthenticationException, IOException {
    req.getSession().invalidate();
    res.getWriter().println(true);

    return null;
  }
}

package com.example.demo.auth.filter;

import static com.example.demo.util.Constants.*;

import com.example.demo.auth.user.ApplicationUser;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain filterChain
  )
    throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();

    // Enumeration<String> attributes = session.getAttributeNames();
    // while (attributes.hasMoreElements()) {
    //   String attribute = (String) attributes.nextElement();
    //   System.out.println(attribute + " : " + session.getAttribute(attribute));
    // }

    ApplicationUser applicationUser = (ApplicationUser) session.getAttribute(
      ApplicationUserInSession
    );

    if (applicationUser != null) {
      UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
        applicationUser,
        applicationUser.getPassword(),
        applicationUser.getAuthorities()
      );

      SecurityContextHolder.getContext().setAuthentication(authUser);
    }

    filterChain.doFilter(request, response);
  }
}

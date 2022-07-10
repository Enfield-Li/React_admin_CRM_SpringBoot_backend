package com.example.demo.auth.filters;

import com.example.demo.auth.users.ApplicationUser;
import com.example.demo.dto.SaleResponseDto;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    System.out.println("AuthenticationFilter called...");
    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();

    ApplicationUser user = (ApplicationUser) session.getAttribute("appUser");
    System.out.println("user: " + user.toString());

    if (user != null) {
      UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
        user,
        user.getPassword(),
        user.getAuthorities()
      );

      SecurityContextHolder.getContext().setAuthentication(authUser);
    }

    filterChain.doFilter(request, response);
    // HttpServletResponse res = (HttpServletResponse) response;
    // res.setContentType("application/json");
    // res.getOutputStream().print(SaleResponseDto.toJSON(user));
  }
}

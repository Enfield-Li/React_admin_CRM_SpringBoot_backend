package com.example.demo.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// @Controller
@RestController
@RequestMapping("/")
public class TemplateController {

  @GetMapping("login")
  public ModelAndView getLogin() {
    ModelAndView modelAndView = new ModelAndView("login");
    return modelAndView;
  }

  // @GetMapping("login")
  // public String getLogin() {
  //   return "login";
  // }

  @GetMapping("courses")
  public ModelAndView getCourses(HttpSession session) {
    /* 
     * SPRING_SECURITY_CONTEXT : 
     * SecurityContextImpl 
      [
        Authentication=
          UsernamePasswordAuthenticationToken 
            [
              Principal=
                { 
                    username='admin1', 
                    password='$2a$10$R6fZPZ5LtEVQ4JL/IHunPuh1L7CwJ6PQjF45J6keDsJ4MSkDihAiW', 
                    grantedAuthorities='[student:write, student:read, course:read, ROLE_ADMIN, course:write]', 
                    isAccountNonExpired='true', 
                    isAccountNonLocked='true', 
                    isCredentialsNonExpired='true', 
                    isEnabled='true'
                }, 
              Credentials=[PROTECTED], 
              Authenticated=true, 
              Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=91E01A65800088A479B688BCFECC9B9D], 
              Granted Authorities=[student:write, student:read, course:read, ROLE_ADMIN, course:write]
            ]
      ]
     */
    Enumeration<String> attributes = session.getAttributeNames();
    while (attributes.hasMoreElements()) {
      String attribute = (String) attributes.nextElement();
      System.out.println(
        attribute + " : " + session.getAttribute(attribute).toString()
      );
    }

    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    String username = auth.getName(); //get logged in username
    System.out.println("username: " + username);

    ModelAndView modelAndView = new ModelAndView("courses");
    return modelAndView;
  }
}

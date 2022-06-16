package com.example.demo.controller;

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
  public ModelAndView getCourses() {
    ModelAndView modelAndView = new ModelAndView("courses");
    return modelAndView;
  }
}

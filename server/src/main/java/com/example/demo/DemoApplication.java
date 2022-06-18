package com.example.demo;

import java.io.IOException;
import javax.swing.DesktopManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(DemoApplication.class, args);
    // openHomePage();
  }

  private static void openHomePage() throws IOException {
    Runtime rt = Runtime.getRuntime();
    rt.exec(
      "rundll32 url.dll,FileProtocolHandler " +
      "http://localhost:3080/swagger-ui/index.html"
    );
  }
}

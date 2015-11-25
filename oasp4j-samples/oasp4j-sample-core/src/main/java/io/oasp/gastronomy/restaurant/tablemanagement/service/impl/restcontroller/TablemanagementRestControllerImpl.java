package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TablemanagementRestControllerImpl {

  @RequestMapping("/test")
  public String test() {

    return "Greetings from Spring Boot REST!";
  }

}

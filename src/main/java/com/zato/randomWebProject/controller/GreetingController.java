package com.zato.randomWebProject.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.zato.randomWebProject.data.GreetingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public GreetingModel greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new GreetingModel(counter.incrementAndGet(), String.format(template, name));
  }

}

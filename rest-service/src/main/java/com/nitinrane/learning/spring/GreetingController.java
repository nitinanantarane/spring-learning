package com.nitinrane.learning.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong ();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(name="name", defaultValue = "World") String name) {
        return new Greeting (counter.incrementAndGet (), name);
    }
}

package org.sevenorganization.int20h2023be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Int20H2023BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Int20H2023BeApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<String> hello(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name
    ) {
        return ResponseEntity.ok("Hello, %s!".formatted(name));
    }
}

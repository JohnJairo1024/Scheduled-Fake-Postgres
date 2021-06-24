package com.sample.postgress.controller;


import com.sample.postgress.models.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Health getHealth() {
        return Health.green();
    }

}

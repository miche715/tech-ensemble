package com.miche.techensemblemain.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/")
    public ResponseEntity<String> getUserList() {
        kafkaTemplate.send("topic-100", "asd");

        return ResponseEntity.ok("요청 전달");
    }
}
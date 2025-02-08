package com.miche.techensemblesub1.api.user.controller;

import com.miche.techensemblesub1.api.user.record.UserListResponseRecord;
import com.miche.techensemblesub1.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserListResponseRecord>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }
}
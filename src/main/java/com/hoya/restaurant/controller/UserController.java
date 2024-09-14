package com.hoya.restaurant.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoya.restaurant.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
@Tag(name = "사용자 관리")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Map<String, String>> login(
            @Parameter(description = "사용자의 uid", required = true) @RequestParam("uid") String uid,

            @Parameter(description = "사용자의 password", required = true) @RequestParam("password") String password,

            HttpSession session) {
        return userService.login(uid, password, session);
    }
}

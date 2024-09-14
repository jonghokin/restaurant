package com.hoya.restaurant.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hoya.restaurant.entity.User;
import com.hoya.restaurant.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Map<String, String>> login(String uid, String password, HttpSession session) {
        User user = userRepository.findById(uid).orElse(null);
        Map<String, String> response = new HashMap<>();

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("uid", uid);
            session.setAttribute("role", user.getRole().name());

            response.put("uid", uid);
            response.put("role", user.getRole().name());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패");
            return ResponseEntity.status(401).body(response); // 401 Unauthorized
        }
    }
}

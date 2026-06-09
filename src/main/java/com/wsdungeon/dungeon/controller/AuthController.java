package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.User;
import com.wsdungeon.dungeon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // jo returno json
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register") // RequestBody sanem json failu no browsera un partaisa objektaa - Map<String, String> nozime ka tas bus vnk "key" : "value" jsons
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> body) {
        User user = userService.register(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of("message", "Registered successfully.", "userId", user.getId())); // javascriptam vajag user id lai ieliktu session storage
    }

    // ResponseEntity - HTTP response - satur status code un kkadu jebkadu informaciju. status code vajag lai izsekotu kludas u.c.

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        return userService.login(body.get("username"), body.get("password"))
                .map(user -> ResponseEntity.ok(Map.of("message", "Login successful.", "userId", user.getId()))) // login atgriez user ja viss atbilst - ja ir tad ResponseEntity.ok
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Invalid credentials."))); // citadak nelogojas in
    }

}

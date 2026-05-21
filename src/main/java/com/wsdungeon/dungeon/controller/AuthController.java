package com.wsdungeon.dungeon.controller;

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
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        userService.register(body.get("username"), body.get("password"));
        return ResponseEntity.ok("Registered successfully."); // ResponseEntity var izmantot javascripts
    }

    // ResponseEntity - HTTP response - satur status code un kkadu jebkadu informaciju. status code vajag lai izsekotu kludas u.c.

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        return userService.login(body.get("username"), body.get("password"))
                .map(user -> ResponseEntity.ok("Login successful.")) // login atgriez user ja viss atbilst - ja ir tad ResponseEntity.ok
                .orElse(ResponseEntity.status(401).body("Invalid credentials.")); // citadak nelogojas in
    }

}

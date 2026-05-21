package com.wsdungeon.dungeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    // forward ir lai smuki pasniegtu lapu
    // redirect ir lai aizvestu uz citu endpointu

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    @GetMapping("/gameroom/join")
    public String joinGameroom() {
        return "forward:/joingame.html";
    }

    @GetMapping("/gameroom/{joinCode}")
    public String gameroom(@PathVariable String joinCode) {
        return "forward:/gameroom.html";
    }

    @GetMapping("/gameroom/start")
    public String startGameroom() {
        return "forward:/gameroom.html";
    }

    @GetMapping("/game/{sessionId}")
    public String game(@PathVariable String sessionId) {
        return "forward:/game.html";
    }

    @GetMapping("/about")
    public String about() {
        return "forward:/about.html";
    }

    @GetMapping("/missions")
    public String missions() {
        return "forward:/missionselect.html";
    }
}

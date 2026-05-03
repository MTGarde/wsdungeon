package com.wsdungeon.dungeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DungeonController {
    // forward ir lai smuki pasniegtu lapu
    // redirect ir lai aizvestu uz citu endpointu

    @GetMapping("/")
    public String index () {
        return "forward:/index.html";
    }

}

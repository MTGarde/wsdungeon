package com.wsdungeon.dungeon;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DungeonController {

    @GetMapping("/")
    public String index () {
        return "client";
    }

}

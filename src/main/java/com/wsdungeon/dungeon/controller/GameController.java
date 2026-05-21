package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/game/{roomId}")
    public ResponseEntity<GameResponse> gameLoop(@PathVariable String id, @RequestBody Map<String, String> body) throws IOException {
        GameResponse response = gameService.handleCommand(id, body.get("command"));
        return ResponseEntity.ok(response);
    }
}

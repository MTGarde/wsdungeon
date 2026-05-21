package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;

    @PostMapping("/gameroom/start")
    public ResponseEntity<GameResponse> createGameRoom() {

        return ResponseEntity.ok(new GameResponse("ok"));
    }

    @PostMapping("/gameroom/{joinCode}")
    public ResponseEntity<GameResponse> joinGameRoom(@PathVariable String joinCode) {

        return ResponseEntity.ok(new GameResponse("ok"));
    }

    @PostMapping("/gameroom/{sessionId}/multiplayer")
    public ResponseEntity<GameResponse> openMultiplayer(@PathVariable String sessionId) {

        return ResponseEntity.ok(new GameResponse("ok"));
    }
}

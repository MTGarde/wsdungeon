package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;

    @PostMapping("/gameroom/start")
    public ResponseEntity<GameResponse> createGameRoom(@RequestBody Map<String, String> body) {

        return ResponseEntity.ok(gameRoomService.createGameRoom(body.get("missionId"), body.get("userId")));

    }

    @PostMapping("/gameroom/{joinCode}") // joingame.js join pogas nospiesana atsuta te body ar userId json formata
    public ResponseEntity<GameResponse> joinGameRoom(@PathVariable String joinCode, @RequestBody Map<String, String> body) {

        return ResponseEntity.ok(gameRoomService.joinGameRoom(joinCode, body.get("userId")));

    }

    @PostMapping("/gameroom/{sessionId}/multiplayer")
    public ResponseEntity<GameResponse> openMultiplayer(@PathVariable String sessionId) {

        return ResponseEntity.ok(gameRoomService.openMultiplayer(sessionId));

    }
}

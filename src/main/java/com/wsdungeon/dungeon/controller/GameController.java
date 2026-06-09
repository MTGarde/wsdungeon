package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.model.GameSession;
import com.wsdungeon.dungeon.repo.GameSessionRepository;
import com.wsdungeon.dungeon.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@RestController
public class GameController {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private GameService gameService;

    @GetMapping("/game/start/{sessionId}") // kad uzsak speli
    public ResponseEntity<GameResponse> getGameState(@PathVariable String sessionId) throws IOException {
        GameSession session = gameSessionRepository.findById(sessionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found.")); // atrod sesiju prieks startGame funkcijas
        GameResponse response = gameService.startGame(session);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/game/{sessionId}/command")
    public ResponseEntity<GameResponse> command(@PathVariable String sessionId, @RequestBody Map<String, String> body) throws IOException {
        GameResponse response = gameService.handleCommand(sessionId, body.get("command")); // command no javascripta
        return ResponseEntity.ok(response);
    }

}

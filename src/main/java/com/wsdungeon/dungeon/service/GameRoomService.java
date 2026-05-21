package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.GameSession;
import com.wsdungeon.dungeon.model.User;
import com.wsdungeon.dungeon.repo.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameRoomService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    public ResponseEntity<String> createGameRoom (String missionId, User user) {
        GameSession session = new GameSession(user, missionId);
        gameSessionRepository.save(session);
        return ResponseEntity.ok("Session created");
    }

    public ResponseEntity<String> joinGameRoom (String code, User user) {
        if (gameSessionRepository.existsByJoinCodeAndMultiplayer(code, true)) {
            GameSession session = gameSessionRepository.findByJoinCode(code).get(); // vajag get(), jo repo atgriez optional
            session.addPlayerToList(user);
            gameSessionRepository.save(session); // save updato jau esosos un pievieno jaunos entryjus
            return ResponseEntity.ok("User joined session.");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> openMultiplayer (String sessionId) {
        GameSession session = gameSessionRepository.findById(sessionId).get();
        session.setMultiplayer(true);
        gameSessionRepository.save(session);
        return ResponseEntity.ok("Multiplayer enabled.");
    }

    private String generateCode () {
        Random rand = new Random();
        String code;

        do {
            code = "";
            for (int i = 0; i < 6; i++) { // uzgenere 6 ciparu string
                code += rand.nextInt(10); // 6 ciparu kods 0-9
            }
        } while (gameSessionRepository.existsByJoinCode(code)); // ja tads kods jau eksiste tad taisa velreiz

        return code;
    }
}

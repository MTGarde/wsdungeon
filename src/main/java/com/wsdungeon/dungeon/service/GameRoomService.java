package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.model.GameSession;
import com.wsdungeon.dungeon.model.User;
import com.wsdungeon.dungeon.repo.GameSessionRepository;
import com.wsdungeon.dungeon.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Service
public class GameRoomService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public GameResponse createGameRoom (String missionId, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        GameSession session = new GameSession(user, missionId); // party leader, mission id
        session.addPlayerToList(user);
        gameSessionRepository.save(session);

        return new GameResponse("Game room created!", session.getId());
    }

    public GameResponse joinGameRoom (String code, String userId) {

        GameSession session = gameSessionRepository.findByJoinCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid join code."));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        session.addPlayerToList(user);
        gameSessionRepository.save(session);

        return new GameResponse("Player has joined the game.");

    }

    public GameResponse openMultiplayer (String sessionId) {
        GameSession session = gameSessionRepository.findById(sessionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game session not found."));
        session.setMultiplayer(true);
        session.setJoinCode(generateCode());
        gameSessionRepository.save(session);

        return new GameResponse("Game opened to multiplayer.");
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

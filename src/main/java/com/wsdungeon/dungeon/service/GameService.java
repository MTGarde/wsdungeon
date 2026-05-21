package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.*;
import com.wsdungeon.dungeon.repo.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class GameService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private MissionLoader missionLoader;

    public GameResponse startGame (GameSession session) throws IOException {
//        MissionMap map = missionLoader.loadMap(missionId);
//        Room startRoom = missionLoader.loadRoom(missionId, map.getStart());
//
//        // TODO kka izkontrolet ka ja eksiste tad pievieno pec sesijas roomId
//        // ja eksiste pec koda vai roomId tad pievieno speletaju sarakstam



        return new GameResponse("Begin.");
    }

    public GameResponse handleCommand(String sessionId, String input) {
        String[] tokens = input.toLowerCase().trim().split(" ");
        String action = tokens[0];
        String target = tokens.length > 1 ? tokens[1] : null;

        GameSession session = gameSessionRepository.findById(sessionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found."));


        return switch (action) {
            case "go" -> handleGo(session, target);
            case "look" -> handleLook(session);
            case "help" -> handleHelp();
            default -> new GameResponse("You can't do that. Try 'help' for a list of commands.");
        };
    }

    public GameResponse handleGo(GameSession session, String direction) {
        if (direction == null) {
            return new GameResponse("Where are you trying to go?");
        }
        // parbauda vai ir tads direction
        if (missionLoader.loadMap(session.getMissionId()).getRoomDirections(session.getCurrentRoomId()).containsKey(direction)) {
            session.setCurrentRoomId(missionLoader.loadMap(session.getMissionId()).getRoomDirections(session.getCurrentRoomId()).get(direction)); // updato current room sesijai
            gameSessionRepository.save(session);
        }
        else { // ja nav tads directions
            return new GameResponse("You can't go there");
        }
        // return jaunas istabas aprakstu // TODO uztaisit istabu failos enter aprakstu maybe ko ieliek message vieta.
        return new GameResponse("You go " + direction + ".", missionLoader.loadRoom(session.getMissionId(), session.getCurrentRoomId()));
    }

    public GameResponse handleLook(GameSession session) {
        // return esosas istabas aprakstu
        return new GameResponse("You look around", missionLoader.loadRoom(session.getMissionId(), session.getCurrentRoomId()));
    }

    public GameResponse handleHelp() {
        return new GameResponse("go [direction], look around, help");
    }
}

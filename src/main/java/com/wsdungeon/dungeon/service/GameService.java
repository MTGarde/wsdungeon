package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.*;
import com.wsdungeon.dungeon.model.JSONclasses.Mission;
import com.wsdungeon.dungeon.model.enums.DescriptionType;
import com.wsdungeon.dungeon.repo.GameSessionRepository;
import com.wsdungeon.dungeon.repo.RoomInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private RoomInstanceRepository roomInstanceRepository;

    @Autowired
    private MissionLoader missionLoader;

    public GameResponse startGame (GameSession session) throws IOException {

        if (session.getCurrentRoom() == null) { // ja sesija tiek uzsakta pirmo reizi tad current room ir jabut start room
            Mission map = missionLoader.loadMission(session.getMissionId());
            RoomInstance startRoom = createRoomInstance(session.getMissionId(), map.getStart());
            session.addRoomProgress(startRoom);
            gameSessionRepository.save(session); // ja kkas mainas tad jasaglaba
        }

        return new GameResponse("Mission start.", session.getCurrentRoom(), missionLoader.loadRoom(session.getCurrentRoom().getRoomId()));
    }

    public GameResponse handleCommand(String sessionId, String input) throws IOException {
        String[] tokens = input.toLowerCase().trim().split(" ");
        String action = tokens[0];
        String target = tokens.length > 1 ? tokens[1] : null;

        GameSession session = gameSessionRepository.findById(sessionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found."));

        // TODO take, fight, use item vai use item object, equip, inspect, say
        // TODO ja sesijas battlestate ir true, tad nelauj darit default darbibas
        // TODO attack, guard, flee, consume (use)? // parejas battle funkcijas battle servica
        return switch (action) {
            case "go" -> handleGo(session, target);
            case "look" -> handleLook(session, target);
            case "help" -> handleHelp();
            default -> new GameResponse("You can't do that. Try 'help' for a list of commands.");
        };
    }

    public GameResponse handleGo(GameSession session, String direction) throws IOException {
        if (direction == null) {
            return new GameResponse("Where are you trying to go?");
        }

        if (session.getCurrentRoom().getExits().containsKey(direction)) { // ja no pasreizejas telpas ir tads direction
            if (!session.getRoomProgress().containsKey(session.getCurrentRoom().getExits().get(direction))) { // ja tada istaba nav explorota
                session.addRoomProgress(createRoomInstance(session.getMissionId(), session.getCurrentRoom().getExits().get(direction))); // izveido jaunu instanci
            } else {
                session.setCurrentRoom(session.getRoomProgress().get(session.getCurrentRoom().getExits().get(direction)));
            }

            GameResponse response = new GameResponse("You go " + direction + ". " + session.getCurrentRoom().getCurrentDescription(missionLoader.loadRoom(session.getCurrentRoom().getRoomId())));
            gameSessionRepository.save(session); // saglaba sesijas un room instances izmainas db
            return response;
        }

        return new GameResponse("You can't go there");
    }

    public GameResponse handleLook(GameSession session, String object)  throws IOException {
        if ( object == null || object.equals("around") ) { // izvada look aprakstu, izejas un objektus?
            String composedDescription = "You look around. ";
            composedDescription = composedDescription.concat(missionLoader.loadRoom(session.getCurrentRoom().getRoomId()).getDescriptions().get(DescriptionType.LOOK)); // telpas apraksts
            if (!session.getCurrentRoom().getInteractives().isEmpty()) { // ja telpa ir objekti
                composedDescription = composedDescription.concat("\nObjects of interest: ");
                for (String s : session.getCurrentRoom().getInteractives().keySet()) {
                    composedDescription = composedDescription.concat(missionLoader.loadInteractible(s).getName() + ", ");
                }
                composedDescription = composedDescription.substring(0, composedDescription.length() - 2); // nonem pedejo komatu
            }
            composedDescription = composedDescription.concat("\nYou can go " + session.getCurrentRoom().getExitsString() + "."); // virzieni uz kuriem var doties

            session.getCurrentRoom().setCurrentDescription(DescriptionType.UPDATED);
            gameSessionRepository.save(session);

            return new GameResponse(composedDescription);
        } else {
            if (session.getCurrentRoom().getInteractives().containsKey(object)) { // ja istaba ir tads objekts
                // tad izvada objekta aprakstu
                return new GameResponse("You look at " + object + ". " + missionLoader.loadInteractible(object).getDescriptions().get(DescriptionType.LOOK));
            } //citadak izvada, ka  nav tadas lietas
        }
        return new GameResponse("What are you trying to see?");
    }

    public GameResponse handleHelp() {
        return new GameResponse("go [direction], look around, help");
    }

    private RoomInstance createRoomInstance (String missionId, String roomId) throws IOException {
        Mission mission = missionLoader.loadMission(missionId);

        RoomInstance instance = new RoomInstance(roomId);

        Map<String, String> exits = mission.getRoomDirections(roomId);
        List<String> enemies = mission.getRoomEnemies(roomId);
        Map<String, Integer> items = mission.getRoomItems(roomId);
        List<String> interactives = mission.getRoomInteractibles(roomId);

        instance.addData(
                exits != null ? exits : new HashMap<>(),
                enemies != null ? enemies : new ArrayList<>(),
                items != null ? items : new HashMap<>(),
                interactives != null ? interactives : new  ArrayList<>()
        );

        roomInstanceRepository.save(instance);

        return instance;
    }

}

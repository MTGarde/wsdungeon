package com.wsdungeon.dungeon.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameResponse {
    private String message;
    private Room currentRoom;
    private Map<String, String> things = new HashMap<>();

    public GameResponse (String message) {
        setMessage(message);
    }

    public GameResponse (String message, Room room) {
        setMessage(message);
        setCurrentRoom(room);
    }

    public GameResponse (String message, Map<String, String> things) {
        setMessage(message);
        setThings(things);
    }
}

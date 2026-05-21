package com.wsdungeon.dungeon.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GameResponse {
    private String message;
    private Room currentRoom;

    public GameResponse (String message) {
        setMessage(message);
    }

    public GameResponse (String message, Room room) {
        setMessage(message);
        setCurrentRoom(room);
    }
}

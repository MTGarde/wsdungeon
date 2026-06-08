package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.JSONclasses.Room;
import lombok.Data;

import java.util.List;

@Data
public class GameResponse {
    private String message;
    private RoomInstance currentRoom;
    private String description;
    private List<String> exits;

    public GameResponse (String message) {
        setMessage(message);
    }

    public GameResponse (String message, RoomInstance roomInstance, Room room) {
        setMessage(message);
        setCurrentRoom(roomInstance);
        setDescription(roomInstance.getCurrentDescription(room));
    }

}

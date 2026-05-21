package com.wsdungeon.dungeon.model;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MissionMap {
    String id;
    String title;
    String description;
    String start;
    Map<String, Map<String, String>> roomConnections; // roomId : {direction : destinationId}

    public Map<String, String> getRoomDirections(String roomId) {
        return roomConnections.get(roomId);
    }
}

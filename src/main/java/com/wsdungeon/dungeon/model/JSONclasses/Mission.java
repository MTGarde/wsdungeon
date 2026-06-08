package com.wsdungeon.dungeon.model.JSONclasses;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Mission {
    String id;
    String title;
    String description;
    String start;
    Map<String, Map<String, String>> roomConnections; // roomId : {direction : destinationId}
    Map<String, List<String>> enemies;
    Map<String, Map<String, Integer>> items;
    Map<String, List<String>> interactibles;

    public Map<String, String> getRoomDirections(String roomId) {
        return roomConnections.get(roomId);
    }

    public List<String> getRoomEnemies(String roomId) {
        return enemies.get(roomId);
    }

    public Map<String, Integer> getRoomItems(String roomId) {
        return items.get(roomId);
    }

    public List<String> getRoomInteractibles(String roomId) {
        return interactibles.get(roomId);
    }
}

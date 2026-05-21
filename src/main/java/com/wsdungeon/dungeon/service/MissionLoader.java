package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.MissionMap;
import com.wsdungeon.dungeon.model.Room;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class MissionLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MissionMap loadMap (String missionId) {
        MissionMap mission = objectMapper.readValue(new File("/static/missions/" + missionId + ".json"), MissionMap.class);
        // TODO cachings?
        return mission;
    }

    public Room loadRoom (String missionId, String roomId) {
        Room room = objectMapper.readValue(new File("/static/missions/" + missionId + "/" + roomId + ".json"), Room.class);
        return room;
    }

}

package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.JSONclasses.Room;
import com.wsdungeon.dungeon.model.enums.DescriptionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "room_instances")
public class RoomInstance {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String roomId; // istabas roomId- faila nosaukums

    DescriptionType currentDescription = DescriptionType.ENTER;

    Map<String, String> exits;

    List<String> remainingEnemies;

    Map<String, Integer> items;

    Map<String, Boolean> interactives;

    public String getCurrentDescription (Room room) {
        switch (currentDescription) {
            case ENTER :
                currentDescription = DescriptionType.DEFAULT;
                return room.getDescriptions().get("enterDescription");
            case DEFAULT :
                return room.getDescriptions().get("defaultDescription");
            case LOOK :
                currentDescription = DescriptionType.UPDATED;
                return room.getDescriptions().get("lookDescription");
            case UPDATED :
                return room.getDescriptions().get("updatedDescription");
            default:
                return "Something went wrong - no room description state.";
        }
    }

    public void addData(Map<String, String> exits, List<String> remainingEnemies, Map<String, Integer> items, List<String> interactives) {
        setExits(exits);
        setRemainingEnemies(remainingEnemies);
        setItems(items);
        setInteractives(interactives);
    }

    private void setInteractives (List<String> interactives) {
        for (String interactiveId : interactives) {
            this.interactives.put(interactiveId, false);
        }
    }

    public RoomInstance (String roomId) {
        setRoomId(roomId);
        setCurrentDescription(DescriptionType.ENTER);
    }
}

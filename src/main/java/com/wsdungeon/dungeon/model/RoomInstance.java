package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.JSONclasses.Room;
import com.wsdungeon.dungeon.model.enums.DescriptionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@Table(name = "room_instances")
public class RoomInstance {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String roomId; // istabas roomId- faila nosaukums

    @Enumerated(EnumType.STRING) // uzglaba enumu ka tekstu nevis indeksu
    @Column(nullable = false)
    DescriptionType currentDescription = DescriptionType.ENTER;

    @ElementCollection
    @CollectionTable(name = "room_exits", joinColumns = @JoinColumn(name = "room_instance_id"))
    @MapKeyColumn(name = "direction")
    @Column(name = "destination")
    Map<String, String> exits = new HashMap<>();

    @ElementCollection // tas ir vertibas nevis relationships
    @CollectionTable(name = "room_remaining_enemies", joinColumns = @JoinColumn(name = "room_instance_id"))
    @Column(name = "enemy_id")
    List<String> remainingEnemies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "room_items", joinColumns = @JoinColumn(name = "room_instance_id"))
    @MapKeyColumn(name = "item_id")
    @Column(name = "amount")
    Map<String, Integer> items = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "room_interactives", joinColumns = @JoinColumn(name = "room_instance_id"))
    @MapKeyColumn(name = "interactive_id")
    @Column(name = "used")
    Map<String, Boolean> interactives = new HashMap<>();

    public String getCurrentDescription (Room room) {
        switch (currentDescription) {
            case ENTER :
                currentDescription = DescriptionType.DEFAULT;
                return room.getDescriptions().get(DescriptionType.ENTER);
            case DEFAULT :
                return room.getDescriptions().get(DescriptionType.DEFAULT);
            case LOOK :
                currentDescription = DescriptionType.UPDATED;
                return room.getDescriptions().get(DescriptionType.LOOK);
            case UPDATED :
                return room.getDescriptions().get(DescriptionType.UPDATED);
            default:
                return "Something went wrong - no room description state.";
        }
    }

    public String getExitsString () {
        String result = "";
        for (String k : exits.keySet()) {
            result = result.concat(k + ", ");
        }
        result = !result.isEmpty() ? result.substring(0, result.length() - 2) : "nowhere";
        return result;
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

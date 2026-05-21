package com.wsdungeon.dungeon.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "room_instances")
public class Room {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String roomId; // istabas roomId- faila nosaukums

    @Column
    boolean isLooked = false;
    // todo item un action lists? maps? kkas

    public Room (String roomId) {
        setRoomId(roomId);
    }
}

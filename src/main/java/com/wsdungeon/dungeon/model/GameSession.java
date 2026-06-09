package com.wsdungeon.dungeon.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "game_sessions")
public class GameSession {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "party_leader_id", nullable = false)
    User partyLeader;

    @Column(unique = true)
    String joinCode;
    @Column(nullable = false)
    boolean multiplayer = false;

    @ManyToMany
    @JoinTable(name = "session_players", joinColumns = @JoinColumn(name = "game_session_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> players = new ArrayList<>();

    @Column(nullable = false)
    String missionId;

    @OneToOne
    @JoinColumn(name = "current_room_id")
    RoomInstance currentRoom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "session_id")
    @MapKey(name = "roomId")
    Map<String, RoomInstance> roomProgress = new HashMap<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "battle_state_id")
    BattleState battleState;

    @ManyToMany
    @JoinTable(name = "session_characters", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "character_id"))
    List<GameCharacter> missionCharacters;

    public void addRoomProgress(RoomInstance newRoom) {
        if (roomProgress.containsKey(newRoom.getRoomId())) {
            roomProgress.replace(newRoom.getRoomId(), newRoom); // ja tada jau ir tad aizvieto
        } else { // ja nav tad pievieno
            roomProgress.put(newRoom.getRoomId(), newRoom);
        }

        setCurrentRoom(newRoom); // iestata current room uz istabu kura tiko iegaja
    }


    public void addPlayerToList (User user) {
        players.add(user);
    }

    public GameSession (User user, String missionId) {
        setPartyLeader(user);
        addPlayerToList(user);
        setMissionId(missionId);
    }

    public GameSession (User user, List<User> users, String missionId) {
        setPartyLeader(user);
        setPlayers(users);
        setMissionId(missionId);
    }

    public GameSession (String userId, String missionId) { // TODO temporary
        setMissionId(missionId);
    }
}

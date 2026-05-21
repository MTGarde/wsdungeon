package com.wsdungeon.dungeon.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    String joinCode;
    boolean multiplayer = false;

    @ManyToMany
    @JoinTable(name = "session_players")
    List<User> players = new ArrayList<>();
    // Map<String, String> missionJSON; // missionjson taka viss kas ir faila - maybe vnk labak missionId

    @Column(nullable = false)
    String missionId;

    @Column
    String currentRoomId;

    @OneToMany
    @JoinTable(name = "session_rooms")
    List<Room> roomProgress = new ArrayList<>();

    public void addRoomProgress (GameResponse message) { // pievieno istabu explorotajam sarakstam kad ieiet jauna istaba
        if (!message.getCurrentRoom().getRoomId().isEmpty()) { // ja message ir padota istaba
            if (!containsRoom(message.getCurrentRoom().getRoomId())) { // ja tada jau nav explorota
                roomProgress.add(new Room(message.getCurrentRoom().getRoomId())); // tad pievieno explorotajam sarakstam
            }
        }
        // TODO velak pievienot ari citus parametrus
    }

    private boolean containsRoom (String id) {
        for (Room r : roomProgress) {
            if (r.getRoomId().equals(id)) {
                return true;
            }
        }
        return false;
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

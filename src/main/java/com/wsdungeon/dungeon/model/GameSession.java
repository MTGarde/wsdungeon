package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.JSONclasses.Room;
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
    List<RoomInstance> roomProgress = new ArrayList<>();

    BattleState battleState;

    List<Character> missionCharacters;

    // nomainīt uz roomInstance un tad pievieno to instance sarakjstam
    public void updateRoomProgress(GameResponse message) { // atjauno istabas datus ja kkas mainas gajiena beigas
        if (!message.getCurrentRoom().getRoomId().isEmpty()) { // ja message ir padota istaba
            if (!containsRoom(message.getCurrentRoom().getRoomId())) { // ja tada jau nav explorota
                Room newInstance = new Room(message.getCurrentRoom().getRoomId()); // izveido jaunu instanci
                // TODO velak pievienot ari citus parametrus
                roomProgress.add(newInstance);
            } else if (containsRoom(message.getCurrentRoom().getRoomId())) { // ja ir explorota tada istaba
                //TODO tad updato datus
            }
        }

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

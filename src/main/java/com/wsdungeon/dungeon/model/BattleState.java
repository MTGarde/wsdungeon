package com.wsdungeon.dungeon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "battle_states")
public class BattleState { // TODO anotacijas
    String id;
    String sessionId;
    List<String> initiative; // TODO kko izdarit par secibu, jo man liekas list nav secibas
    int currentTurn;
    Map<String, Integer> enemyCurrentHp;
    Map<String, Map<String, Integer>> enemyStatusEffects;
    boolean active;

}

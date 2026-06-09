package com.wsdungeon.dungeon.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String sessionId;

    @ElementCollection
    @OrderColumn(name = "turn_order") // saglaba secibu db
    @CollectionTable(name = "battle_initiative", joinColumns = @JoinColumn(name = "battle_state_id"))
    @Column(name = "creature_id")
    List<String> initiative;

    @Column(nullable = false)
    int currentTurn;

    @ElementCollection
    @CollectionTable(name = "battle_enemy_hp", joinColumns = @JoinColumn(name = "battle_state_id"))
    @MapKeyColumn(name = "enemy_id")
    @Column(name = "current_hp")
    Map<String, Integer> enemyCurrentHp;

    // Map<String, Map<String, Integer>> enemyStatusEffects; TODO izdomat ka tikt gala ar to, jo db ir problemas ar nested map

    @Column(nullable = false)
    boolean active;

}

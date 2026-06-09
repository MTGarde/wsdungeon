package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.JSONclasses.BattleClass;
import com.wsdungeon.dungeon.model.enums.ItemType;
import com.wsdungeon.dungeon.model.enums.StatsEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class GameCharacter { // TODO anotacijas
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String battleClass;

    @Column(nullable = false)
    int maxHp;

    @Column(nullable = false)
    int currentHp;

    @ElementCollection
    @CollectionTable(name = "character_inventory", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyColumn(name = "item_id")
    @Column(name = "amount")
    Map<String, Integer> inventory;

    @ElementCollection
    @CollectionTable(name = "character_equipment", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyEnumerated(EnumType.STRING) //@MapKeyColumn vieta jo ir enums
    @MapKeyColumn(name = "slot")
    @Column(name = "item_id")
    Map<ItemType, String> equipment;

    @ElementCollection
    @CollectionTable(name = "character_stats", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "stat")
    @Column(name = "value")
    Map<StatsEnum, Integer> stats;

    @ElementCollection
    @CollectionTable(name = "character_status_effects", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyColumn(name = "effect_id")
    @Column(name = "duration")
    Map<String, Integer> statusEffects = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "character_modifiers", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "stat")
    @Column(name = "value")
    Map<StatsEnum, Integer> modifiers;

    @ElementCollection
    @CollectionTable(name = "character_abilities", joinColumns = @JoinColumn(name = "character_id"))
    @Column(name = "ability_id")
    List<String> abilities;



    private void setModifiers () {
        // TODO
    }

    private void levelUp(BattleClass battleClass, int level) {
        abilities.addAll(battleClass.getLevelUps().get(level));
        Random rand = new Random();
        maxHp += rand.nextInt(battleClass.getHp()) + 1;
    }

    public void addData(BattleClass battleClass) {
        Random rand = new Random();
        setMaxHp(rand.nextInt(battleClass.getHp()) + 1);
        setCurrentHp(getMaxHp());
        setInventory(battleClass.getStartingEquipment());
        setAbilities(battleClass.getLevelUps().get(1));
    }

    public GameCharacter(String name, String battleClassId, Map<StatsEnum, Integer> stats) {
        setName(name);
        setBattleClass(battleClassId);
        setStats(stats);
        setModifiers();
    }


}

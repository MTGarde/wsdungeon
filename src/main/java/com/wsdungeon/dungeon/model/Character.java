package com.wsdungeon.dungeon.model;

import com.wsdungeon.dungeon.model.enums.ItemType;
import com.wsdungeon.dungeon.model.enums.StatsEnum;
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
@Table(name = "characters")
public class Character { // TODO anotacijas
    String id;
    String name;
    String battleClass;
    int maxHp;
    int currentHp;
    Map<String, Integer> inventory;
    Map<ItemType, String> equipment;
    Map<StatsEnum, Integer> stats;
    Map<String, Integer> statusEffects;
    Map<StatsEnum, Integer> modifiers;
    List<String> abilities;
}

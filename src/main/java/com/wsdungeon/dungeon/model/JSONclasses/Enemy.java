package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DamageType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Enemy {
    String id;
    String name;
    int maxHp;
    String damage;
    List<String> actions;
    Map<String, Integer> loot;
    Map<DamageType, Integer> damageMitigation;
}

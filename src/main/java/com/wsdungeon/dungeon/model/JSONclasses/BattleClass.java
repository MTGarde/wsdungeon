package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DamageType;
import com.wsdungeon.dungeon.model.enums.StatsEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BattleClass {
    String id;
    Integer hp; // "D10, D6" utt. nosaka, kads kaulins jamet kad ir level ups lai paaugstinatu max hp
    StatsEnum[] proficiency;
    Map<String, Integer> startingEquipment;
    Map<DamageType, Integer> damageMitigation;
    Map<Integer, List<String>> levelUps;
}

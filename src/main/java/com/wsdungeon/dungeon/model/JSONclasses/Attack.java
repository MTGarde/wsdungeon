package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DamageType;
import com.wsdungeon.dungeon.model.enums.StatsEnum;
import lombok.Data;

import java.util.List;

@Data
public class Attack {
    String id;
    String description;
    DamageType type;
    String damage; // "+D10, -D15" utt.
    StatsEnum savingThrow;
    List<String> statusEffects;
}

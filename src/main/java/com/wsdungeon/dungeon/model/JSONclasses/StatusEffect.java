package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DamageType;
import lombok.Data;

@Data
public class StatusEffect {
    String id;
    int duration;
    String strength; // "-D5, +D2" utt.
    DamageType damageType;
}

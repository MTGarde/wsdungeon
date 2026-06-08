package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DescriptionType;
import lombok.Data;

import java.util.Map;

@Data
public class Room {
    String id;
    String title;
    Map<DescriptionType, String> descriptions;
}

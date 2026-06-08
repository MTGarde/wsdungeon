package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.ItemType;
import lombok.Data;

import java.util.Map;

@Data
public class Item {
    String id;
    String name;
    String description;
    ItemType type;
    Map<String, Integer> stats; // "tips" : "vertiba"  tips ir "DEF, HP" u.c.
    int moneyValue; // vertiba par cik naudam var nopirkt vai pardot
}

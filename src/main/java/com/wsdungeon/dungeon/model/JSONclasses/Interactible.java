package com.wsdungeon.dungeon.model.JSONclasses;

import com.wsdungeon.dungeon.model.enums.DescriptionType;
import lombok.Data;

import java.util.Map;

@Data
public class Interactible {
    String id;
    String name;
    String requires;
    Map<String, Integer> contains;
    Map<DescriptionType, String> descriptions; // default - vnk apraksts, look - apskatities tuvak, success - prieksmeta veiksmiga izmantosana, updated - pec izdevusas darbibas
    Map<String, String> unlocks; // virziens un telpas id, ja tas ir durvis
}

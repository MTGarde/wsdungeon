package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.JSONclasses.*;
import com.wsdungeon.dungeon.model.RoomInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MissionLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, Mission> missionCache = new HashMap<>(); // final, jo nevar velak velreiz initializot
    private final Map<String, Room> roomCache = new HashMap<>();
    private final Map<String, Item> itemCache = new HashMap<>();
    private final Map<String, Interactible> interactibleCache = new HashMap<>();
    private final Map<String, Enemy> enemyCache = new HashMap<>();
    private final Map<String, BattleClass> battleClassCache = new HashMap<>();
    private final Map<String, Attack> attackCache = new HashMap<>();
    private final Map<String, StatusEffect> statusEffectCache = new HashMap<>();


    public Mission loadMission(String missionId) throws IOException {

        if (!missionCache.containsKey(missionId)) { // new File strada tikai developmenta, jo tas atrod datora failos C:/something/fails.json
                                                    // deployosana padara projektu par JAR failu, kur nav tads file path tpc vajag ClassPathResource
            ClassPathResource missionFile = new ClassPathResource("/static/missions/" + missionId + ".json");
            Mission mission = objectMapper.readValue(missionFile.getInputStream(), Mission.class);
            missionCache.put(missionId, mission);
        }

        return missionCache.get(missionId);

    }

    public Room loadRoom (String missionId, String roomId) throws IOException{

        if (!roomCache.containsKey(roomId)) {
            ClassPathResource roomFile = new ClassPathResource("/static/missions/" + missionId + "/" + roomId + ".json");
            Room room = objectMapper.readValue(roomFile.getInputStream(), Room.class);
            roomCache.put(roomId, room);
        }

        return roomCache.get(roomId);

    }

    public Item loadItem (String itemId) throws IOException {

        if (!itemCache.containsKey(itemId)) {
            ClassPathResource itemFile = new ClassPathResource("/static/items/" + itemId + ".json");
            Item item = objectMapper.readValue(itemFile.getInputStream(), Item.class);
            itemCache.put(itemId, item);
        }

        return itemCache.get(itemId);

    }

    public Interactible loadInteractible (String interactibleId) throws IOException {

        if (!interactibleCache.containsKey(interactibleId)) {
            ClassPathResource interactibleFile = new ClassPathResource("/static/interactibles/" + interactibleId + ".json");
            Interactible interactible = objectMapper.readValue(interactibleFile.getInputStream(), Interactible.class);
            interactibleCache.put(interactibleId, interactible);
        }

        return interactibleCache.get(interactibleId);

    }

    public Enemy loadEnemy (String enemyId) throws IOException {

        if (!enemyCache.containsKey(enemyId)) {
            ClassPathResource enemyFile = new ClassPathResource("/static/enemies/" + enemyId + ".json");
            Enemy enemy = objectMapper.readValue(enemyFile.getInputStream(), Enemy.class);
            enemyCache.put(enemyId, enemy);
        }

        return enemyCache.get(enemyId);

    }

    public BattleClass loadBattleClass (String battleClassId) throws IOException {

        if (!battleClassCache.containsKey(battleClassId)) {
            ClassPathResource battleClassFile = new ClassPathResource("/static/battleclasses/" + battleClassId + ".json");
            BattleClass battleClass = objectMapper.readValue(battleClassFile.getInputStream(), BattleClass.class);
            battleClassCache.put(battleClassId, battleClass);
        }

        return battleClassCache.get(battleClassId);

    }

    public Attack loadAttack (String attackId) throws IOException {

        if (!attackCache.containsKey(attackId)) {
            ClassPathResource attackFile = new ClassPathResource("/static/attacks/" + attackId + ".json");
            Attack attack = objectMapper.readValue(attackFile.getInputStream(), Attack.class);
            attackCache.put(attackId, attack);
        }

        return attackCache.get(attackId);

    }

    public StatusEffect loadStatusEffect (String statusEffectId) throws IOException {

        if (!statusEffectCache.containsKey(statusEffectId)) {
            ClassPathResource statusEffectFile = new ClassPathResource("/static/statuseffects/" + statusEffectId + ".json");
            StatusEffect statusEffect = objectMapper.readValue(statusEffectFile.getInputStream(), StatusEffect.class);
            statusEffectCache.put(statusEffectId, statusEffect);
        }

        return statusEffectCache.get(statusEffectId);

    }

}

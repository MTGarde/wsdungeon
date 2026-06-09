package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, String> {
}

package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.BattleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleStateRepository extends JpaRepository<BattleState, String> {
}

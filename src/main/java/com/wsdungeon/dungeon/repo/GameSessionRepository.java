package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.GameSession;
import com.wsdungeon.dungeon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, String> {

    Optional<List<GameSession>> findByPartyLeader (User partyLeader);

    boolean existsByJoinCode (String joinCode);

    Optional<GameSession> findByJoinCode (String code);
}

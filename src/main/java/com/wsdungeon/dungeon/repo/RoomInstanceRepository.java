package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.RoomInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInstanceRepository extends JpaRepository<RoomInstance, String> {
}

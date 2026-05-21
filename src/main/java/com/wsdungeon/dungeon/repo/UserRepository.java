package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> { // User - klase, ko apstrada repo. String - primary key tips
    Optional<User> findByUsername(String username);
}

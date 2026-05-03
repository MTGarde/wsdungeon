package com.wsdungeon.dungeon.repo;

import com.wsdungeon.dungeon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> { // User - klase, ko apstrada repo. String - primary key tips
    Optional<User> findByUsername(String username);
}

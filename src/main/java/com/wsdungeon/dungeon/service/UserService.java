package com.wsdungeon.dungeon.service;

import com.wsdungeon.dungeon.model.User;
import com.wsdungeon.dungeon.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired // autowired nozime ka izmanto beanus
    private UserRepository userRepository;

    @Autowired // piem., securityConfiga ir beans encoderim
    private PasswordEncoder passwordEncoder; // beans uzreiz pieskir passwordEncoder to BCrypt...blablabla

    public User register (String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is taken."); // runtimeExceptions ir pilnigi visas kludas laikam
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public Optional<User> login (String username, String password) {
        return userRepository.findByUsername(username).filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}

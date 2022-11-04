package dev.janssenbatista.todolistapi.services;

import dev.janssenbatista.todolistapi.models.User;
import dev.janssenbatista.todolistapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    User saveUser(User user) {
        return userRepository.save(user);
    }

    Optional<User> findUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    void deleteUser(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new RuntimeException("User not found");
        });
    }
}

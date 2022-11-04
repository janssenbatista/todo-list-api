package dev.janssenbatista.todolistapi.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.janssenbatista.todolistapi.dtos.UserDto;
import dev.janssenbatista.todolistapi.models.User;
import dev.janssenbatista.todolistapi.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID userId) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        return ResponseEntity.ok(optionalUser.get());
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(UUID.randomUUID());
        user.setPassword(BCrypt.withDefaults().hashToString(12, userDto.getPassword().toCharArray()));
        user.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUserById(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        var user = optionalUser.get();
        BeanUtils.copyProperties(userDto, user);
        user.setId(userId);
        user.setPassword(BCrypt.withDefaults().hashToString(12, userDto.getPassword().toCharArray()));
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}

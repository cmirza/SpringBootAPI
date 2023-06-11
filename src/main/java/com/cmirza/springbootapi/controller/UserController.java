package com.cmirza.springbootapi.controller;

import com.cmirza.springbootapi.model.User;
import com.cmirza.springbootapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public Page<User> getAllUsers(@RequestParam Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "5"));

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        System.out.println("Updating user with id: " + id);
        System.out.println("Updated user details: " + updatedUser);
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    User savedUser = userRepository.save(user);
                    return new ResponseEntity<>(savedUser, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    updatedUser.setId(id);
                    User savedUser = userRepository.save(updatedUser);
                    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public Page<User> searchUsers(@RequestParam String keyword, @RequestParam Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "5"));

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.search(keyword, pageable);
    }
}

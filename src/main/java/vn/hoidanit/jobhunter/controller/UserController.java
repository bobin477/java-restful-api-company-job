package vn.hoidanit.jobhunter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> ListUser = this.userService.getAll();
        return ResponseEntity.ok(ListUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User userById = this.userService.GetUserById(id);
        return ResponseEntity.ok(userById);
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user) {
        this.userService.handleUpdateUserById(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User user1 = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws IdInvalidException {
        if(id > 1000){
            throw new IdInvalidException("id lon hong 1000");
        }
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
    }

}

package vn.hoidanit.jobhunter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;

@RestController
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all-users")
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
        User user1 = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
    }
}

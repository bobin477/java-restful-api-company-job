package vn.hoidanit.jobhunter.controller;


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


    @GetMapping("/all-user")
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable Long id) {
        return this.userService.GetUserById(id);
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        return this.userService.handleUpdateUserById(user);
    }

    @PostMapping("/user")
    public User createNewUser(@RequestBody User user) {
        return this.userService.handleCreateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUserById(id);
        return "User with id deleted " + id;
    }
}

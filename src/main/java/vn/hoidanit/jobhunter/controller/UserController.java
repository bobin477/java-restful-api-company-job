package vn.hoidanit.jobhunter.controller;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.user.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.user.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.user.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.util.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAll(
            @Filter Specification<User> specification, Pageable pageable
    ) {

        return ResponseEntity.ok(this.userService.getAll(specification, pageable));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch one users")
    public ResponseEntity<ResUserDTO> getById(@PathVariable Long id) throws IdInvalidException {
        User userById = this.userService.GetUserById(id);

        if (userById == null) {
            throw new IdInvalidException("User with id " + id + " not found");
        }

        return ResponseEntity.ok(this.userService.convertToResUserDTO(userById));
    }

    @PostMapping("/users/create")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User userRequest) throws IdInvalidException {
        boolean isEmailExit = this.userService.isEmailExist(userRequest.getEmail());

        if (isEmailExit) {
            throw new IdInvalidException(userRequest.getEmail() + " da ton tai");
        }
        userRequest.setPassword(this.passwordEncoder.encode(userRequest.getPassword()));
        User user1 = this.userService.handleCreateUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(user1));
    }

    @PutMapping("/users")
    @ApiMessage("update all users")
    public ResponseEntity<ResUpdateUserDTO> update(@RequestBody User user) throws IdInvalidException {
        User userById = this.userService.handleUpdateUserById(user);

        if (userById == null) {
            throw new IdInvalidException("User with id " + user.getId() + " not found");
        }

        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(userById));
    }


    @DeleteMapping("/users/{id}")
    @ApiMessage("delete a user")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws IdInvalidException {
        User currentUser = this.userService.GetUserById(id);
        if (currentUser == null) {
            throw new IdInvalidException("User " + id + " nao existe no user");
        }

        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
    }

}

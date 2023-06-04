package com.company.controller;

import com.company.annotation.ExecutionTime;
import com.company.dto.ResponseWrapper;
import com.company.dto.UserDTO;
import com.company.exception.TicketingProjectException;
import com.company.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController",description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExecutionTime
    @GetMapping
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "Get users")
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved", userList, HttpStatus.OK));
    }

    @ExecutionTime
    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get user by username")
    public ResponseEntity<ResponseWrapper> getUserByUSerName(@PathVariable("username") String userName) {
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved", user, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is saved successfully", HttpStatus.CREATED));
    }

    @PutMapping("{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable("username") String userName, @RequestBody UserDTO user) {

        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", HttpStatus.OK));
    }

    @DeleteMapping("{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String userName) throws TicketingProjectException {
        userService.delete(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));
    }

}

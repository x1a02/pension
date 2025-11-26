package com.x1a02.pension.controller;

import com.x1a02.pension.common.Result;
import com.x1a02.pension.dto.LoginRequest;
import com.x1a02.pension.entity.User;
import com.x1a02.pension.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("Invalid username or password");
    }

    @PostMapping("/add")
    public Result<Boolean> addUser(@RequestBody User user) {
        boolean success = userService.addUser(user);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to add user");
    }

    @PutMapping("/update")
    public Result<Boolean> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update user");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to delete user");
    }

    @GetMapping("/get/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("User not found");
    }

    @GetMapping("/list")
    public Result<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(null);
        }
        return Result.success(list);
    }

    @GetMapping("/staff")
    public Result<List<User>> getStaffUsers() {
        List<User> list = userService.getStaffUsers();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(null);
        }
        return Result.success(list);
    }

    @GetMapping("/family")
    public Result<List<User>> getFamilyUsers() {
        List<User> list = userService.getFamilyUsers();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(null);
        }
        return Result.success(list);
    }
}

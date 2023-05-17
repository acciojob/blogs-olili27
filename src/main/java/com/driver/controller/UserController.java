package com.driver.controller;

import com.driver.models.User;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String password) {
        // create a new user with given username and password
        User user = userService.createUser(username, password);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        // delete user using deleteById
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam Integer id, @RequestParam String password) {
        // update password of given user
        User user = userService.updateUser(id, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

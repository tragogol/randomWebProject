package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public ResponseEntity<?> userList(Users model) {
        StringBuilder returnString = new StringBuilder();
        List<Users> usersList = userService.allUsers();
        for (Users users : usersList) {
            returnString.append(users.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnString.toString());
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}

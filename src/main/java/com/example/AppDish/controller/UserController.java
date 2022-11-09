package com.example.AppDish.controller;

import com.example.AppDish.entity.Recipe;
import com.example.AppDish.entity.User;
import com.example.AppDish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    public void createUser(@ModelAttribute User user) {
        userRepository.save(user);
    }

    @PostMapping("/signedUp/success")
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "homeNotSignedIn";
    }

    @GetMapping("/signedUp")
    public String signedIn(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signedUp";
    }

    @GetMapping("/{userId}")
    public User readUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not found.");
    }

    @GetMapping("/loginTest")
    public String showForm(User user) {
        return "loginTest";
    }

    @PostMapping("/za")
    public String validateLoginInfo(Model model, @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        model.addAttribute("user", user);
        return "homeNotSignedIn";
    }
    @GetMapping({"/{userid}/change-user"})
    public String updateUser(@PathVariable("userid") Long userid,  @ModelAttribute("user") User user,Model muser) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid); // Get the User
        if (currentuser.isPresent()) {
            user.setId(currentuser.get().getId());
            user.setFirstname(currentuser.get().getFirstname());
            user.setLastname(currentuser.get().getLastname());
            user.setUsername(currentuser.get().getUsername());
            user.setEmail(currentuser.get().getEmail());
            user.setPassword(currentuser.get().getPassword());
            muser.addAttribute("users", currentuser.get());
            return "changeUser";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    }

    @PostMapping({"/{userid}/succeedChanged"})
    public String updateUserSave(@PathVariable("userid") Long userid, @ModelAttribute("user") User user, Model model){
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isPresent()) {
            user.setId(currentuser.get().getId());
            userRepository.save(user);
            model.addAttribute("users", currentuser.get());
            return "userInfo";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    }

    @RequestMapping("/{id}/userInfo")
    public String updateUser(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("id") Long userid, Model model) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isPresent()) {
            user.setId(currentuser.get().getId());
            user.setEmail(currentuser.get().getEmail());
            user.setPassword(currentuser.get().getPassword());
            user.setUsername(currentuser.get().getUsername());
            user.setFirstname(currentuser.get().getFirstname());
            user.setLastname(currentuser.get().getLastname());
            return "userInfo";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    }

    @RequestMapping("/{id}/userDelete")
    public String deleteUser(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("id") Long userid, Model model) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isPresent()) {
            user.setId(currentuser.get().getId());
            user.setEmail(currentuser.get().getEmail());
            user.setPassword(currentuser.get().getPassword());
            user.setUsername(currentuser.get().getUsername());
            user.setFirstname(currentuser.get().getFirstname());
            user.setLastname(currentuser.get().getLastname());
            return "deleteUser";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    }

    @GetMapping({"/{userid}/succeedDelete"})
    public String deleteUserConfirm(@PathVariable("userid") Long userid, @ModelAttribute("user") User user, Model model){
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isPresent()) {
            user.setId(currentuser.get().getId());
            userRepository.delete(user);
            return "homeNotSignedIn";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    }

}

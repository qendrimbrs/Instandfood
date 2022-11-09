package com.example.AppDish.controller;


import com.example.AppDish.dto.LoginDto;
import com.example.AppDish.entity.Recipe;
import com.example.AppDish.entity.User;
import com.example.AppDish.repository.RecipeRepository;
import com.example.AppDish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class HomepageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;
//    @RequestMapping ("/home/{id}")
//    public String homePageSignedIn(@ModelAttribute("user") @PathVariable("id") Long userid) {
//        Optional<User> currentuser = userRepository.findById(userid);
//        if (currentuser.isEmpty())  {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
//        } else {
//            return "homeSignedIn";
//        }
//    }

    @RequestMapping("/home")
    public String homePageNotSignedIn(@ModelAttribute("recipe")Recipe recipe, Model model) {
        List<Recipe> recipeList = recipeRepository.findAll();
        model.addAttribute("recipeList", recipeList);
        return "homeNotSignedIn";
    }

    @GetMapping("/home/{id}")
    public String homePage(Model model, @ModelAttribute("user") User user, @PathVariable("id") Long userid, @ModelAttribute("recipe") Recipe recipe) {
        List<Recipe> recipeList = recipeRepository.findAll();
        model.addAttribute("recipeList", recipeList);
        Optional<User> currentuser = userRepository.findById(userid);
        if (currentuser.isPresent()) {
            model.addAttribute("user", currentuser.get());
            return "homeSignedIn";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        }
    }

    @RequestMapping({"/home/{id}"})
    public String openHome(@ModelAttribute("user") User user, @PathVariable("id") Long userid, Model model, @ModelAttribute("recipe") Recipe recipe) {
        Optional<User> currentuser = userRepository.findById(userid);
        List<Recipe> recipeList = recipeRepository.findAll();
        model.addAttribute("recipeList", recipeList);
        if (currentuser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            model.addAttribute("user", currentuser.get());
            return "homeSignedIn";
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/home/login")
    public String login(@ModelAttribute("LoginDto") LoginDto loginDto, Model model, @ModelAttribute("user") User user) {
        User currentuser = userRepository.findOneByUsername(loginDto.getUsername());
        model.addAttribute("currentuser", currentuser);
        if (currentuser.getPassword().equals(loginDto.getPassword())) {
            return "confirmLogin";
        } else {
            return "loginerror";
        }
    }
}
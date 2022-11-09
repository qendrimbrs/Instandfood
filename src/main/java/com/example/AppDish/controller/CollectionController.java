package com.example.AppDish.controller;

import com.example.AppDish.entity.Collection;
import com.example.AppDish.entity.Recipe;
import com.example.AppDish.entity.User;
import com.example.AppDish.repository.CollectionRepository;
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
@RequestMapping("collection")
public class CollectionController {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CollectionRepository collectionRepository;


    @RequestMapping("/{userid}/collection")
    public String openCreateRecipe(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("userid") Long userid, Model model) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);

        if (currentuser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            model.addAttribute("currentuser", currentuser);
            return "createCollection";
        }
    }

    @GetMapping("/{userid}/collection")
    public String CreateCollection(Model modeluser ,Model model, @PathVariable("userid") Long userid, @ModelAttribute("user") User user, @ModelAttribute("recipe") Recipe recipe, @ModelAttribute("collection")Collection collection) {
        Optional<User> currentuser = userRepository.findById(userid);
        List<Collection> collectionList = collectionRepository.findAll();
        if (currentuser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            Collection collection1 = new Collection();
            collection1.setUser(currentuser.get());
            model.addAttribute("collectionl", collectionList);
            model.addAttribute("user1", currentuser.get());
            model.addAttribute("collection", collection1);
            modeluser.addAttribute("currentuser", currentuser.get());
            return "createCollection";
        }

    }

    @RequestMapping("/{userid}/collection/confirm")
    public String confirmCollection(Model model, @PathVariable("userid") Long userid, @ModelAttribute("user") User user, @ModelAttribute("recipe") Recipe recipe, @ModelAttribute("collection")Collection collection){
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        model.addAttribute("user1", currentuser.get());
        collection.setUser(currentuser.get());
        collectionRepository.save(collection);
        return "confirmCollection";
    }

    @RequestMapping("/{userid}/collection/view")
    public String showCollection(Model model, @PathVariable("userid") Long userid, @ModelAttribute("user") User user, @ModelAttribute("recipe") Recipe recipe){
        return "viewCollection";
    }
}
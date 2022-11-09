package com.example.AppDish.controller;

import com.example.AppDish.dto.LoginDto;
import com.example.AppDish.dto.RatingDto;
import com.example.AppDish.entity.Comment;
import com.example.AppDish.entity.Rating;
import com.example.AppDish.entity.Recipe;
import com.example.AppDish.dto.CommentDto;
import com.example.AppDish.entity.User;
import com.example.AppDish.repository.CommentRepository;
import com.example.AppDish.repository.RatingRepository;
import com.example.AppDish.repository.RecipeRepository;
import com.example.AppDish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RatingRepository ratingRepository;

    // Save the Created Recipe
    @PostMapping("/user/{id}/recipe")
    public String savedRecipe(@ModelAttribute("recipe") Recipe recipe, @ModelAttribute("user") @PathVariable("id") Long userid, Model model) {
        // We just need to save the
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        model.addAttribute("user", currentuser.get());
        recipe.setUser(currentuser.get());
        recipeRepository.save(recipe);
        return "succeedRecipe";
    }

    @GetMapping("/readRecipe/{recipeId}")
    public String readRecipe(Model model, @PathVariable Long recipeId, @ModelAttribute("user") User user, @ModelAttribute("recipe") Recipe recipe) {
        // model.addAttribute("AuthenticationDto", new AuthenticationDto());
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId);
        model.addAttribute("recipe1", recipe1);
        model.addAttribute("CommentDto", new CommentDto());
        model.addAttribute("LoginDto", new LoginDto());
        model.addAttribute("recipeName", recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe not found")).getTitle());
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("rating", new RatingDto());
        List<Comment> recipeList = commentRepository.findByRecipe_RecipeId(recipeId);
        model.addAttribute("comments", recipeList);
        double rate = ratingRepository.findByRecipe(recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe not found")))
                .stream().mapToInt(Rating::getRating).average().orElse(0.0);
        model.addAttribute("rate", rate);
        //User user = userRepository.findOneByUsername(username);

        return "recipe";
    }

    // Read a present Recipe
    @RequestMapping("/user/{id}/recipe/{recipeId}")
    public String redRecipeRe(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("id") Long userid, @ModelAttribute("recipe") Recipe recipe, @PathVariable("recipeId") Long recipeId, Model model) {
        // Find the present Recipe with the UserID
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId);
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (recipe1.isPresent() && currentuser.isPresent()) {
            // We put found Recipe in the recipe Object
            recipe.setRecipeid(recipe1.get().getRecipeid());
            recipe.setTitle(recipe1.get().getTitle());
            recipe.setIngredient(recipe1.get().getIngredient());
            recipe.setPreparation(recipe1.get().getPreparation());
            model.addAttribute("users", currentuser.get());
            return "userRecipe";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
    }


    // Save the Update Recipe
    @PostMapping({"/user/{userid}/succeedChanged/{recipeid}"})
    public String updateRecipeSave(@ModelAttribute("recipe") Recipe recipe, @PathVariable("recipeid") Long recipeId, @ModelAttribute("user") @PathVariable("userid") Long userid, Model model) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId); // Get the Recipe
        if (recipe1.isPresent() && currentuser.isPresent()) {
            recipe.setRecipeid(recipe1.get().getRecipeid()); // User the same RecipeID
            recipe.setUser(currentuser.get());
            recipeRepository.save(recipe); // Save the new Recipe with the same RecipeID
            model.addAttribute("users", currentuser.get());
            return "userRecipe";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
    }


    @PostMapping("/addComment/{id}")
    public String addComment(@ModelAttribute("CommentDto") CommentDto addComment, @PathVariable("id") Long recipeId, Model model) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe not found"));
        User userEntity = userRepository.findOneByUsername(addComment.getUserId());

        if (userEntity != null && userEntity.getPassword().equals(addComment.getPassword())) {
            Comment comment = new Comment(userEntity, recipe, addComment.getComment());
            commentRepository.save(comment);

            return "redirect:/recipes/readRecipe/" + recipeId;
        }

        return "redirect:/login";
    }

    @RequestMapping({"/user/{userid}/recipe/create-recipe"})
    public String openCreateRecipe(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("userid") Long userid) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            return "createRecipee";
        }
    }

    // Creat a new recipe on the "create_recipee" website.
    @GetMapping({"/user/{userid}/recipe/create-recipe"})
    public String creatRecipe(@ModelAttribute("user") com.example.AppDish.entity.User user, @PathVariable("userid") Long userid, Model model, Model modeluser) {
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (currentuser.isEmpty()) { // you can't create a Recipe without a User, so we need to find the User
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            Recipe recipe = new Recipe();
            recipe.setUser(currentuser.get());
            model.addAttribute("recipe", recipe);
            modeluser.addAttribute("user", currentuser.get());
            return "createRecipee";
        }
    }

    // Update a present Recipe
    @GetMapping({"/user/{userid}/change-recipe/{recipeId}/sec"})
    public String updateRecipe(@PathVariable("userid") Long userid, @ModelAttribute("recipe") Recipe recipe, @PathVariable("recipeId") Long recipeId, Model muser, Model mrecipe) {
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId); // Get the Recipe
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (recipe1.isPresent() && currentuser.isPresent()) {
            // Put the Recipe in recipe object, so we can get the information
            recipe.setRecipeid(recipe1.get().getRecipeid());
            recipe.setTitle(recipe1.get().getTitle());
            recipe.setIngredient(recipe1.get().getIngredient());
            recipe.setPreparation(recipe1.get().getPreparation());
            muser.addAttribute("users", currentuser.get());
            return "changeRecipe";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
    }

    @DeleteMapping(path = "/user/recipe/{recipeId}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "recipeId") Long recipeId)
            throws ResponseStatusException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found for this id :: " + recipeId));

        recipeRepository.delete(recipe);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted = iD:" + recipeId, Boolean.TRUE);
        return response;
    }

    @RequestMapping("/{id}/view")
    public String viewRecipe(Model model, @ModelAttribute("recipe") Recipe recipe, @ModelAttribute("user") User user, @PathVariable("id") Long recipeid) {
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeid);
        model.addAttribute("recipe1", recipe1);
        return "recipeView";
    }

    @RequestMapping("/{id}/ownRecipe")
    public String viewOwnRecipes(Model model, @ModelAttribute("user") User user, @ModelAttribute("recipe") Recipe recipe, @PathVariable("id") Long userid) {
        List<Recipe> recipeList = recipeRepository.findAll();
        model.addAttribute("recipel", recipeList);
        return "ownRecipe";
    }


    @RequestMapping({"/search/{id}"})
    public String search(@RequestParam(value = "query") String query, @ModelAttribute("user") User user, @PathVariable("id") Long userid, Model model, @ModelAttribute("recipe") Recipe recipe) {
        Optional<User> currentuser = userRepository.findById(userid);
        List<Recipe> recipeList = recipeRepository.findByTitleLike(query);
        model.addAttribute("recipeList", recipeList);

        System.out.println(query);

        if (currentuser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            model.addAttribute("user", currentuser.get());
            return "homeSignedIn";
        }
    }

    @RequestMapping({"/search"})
    public String searchNotSignedIn(@RequestParam(value = "query") String query, @ModelAttribute("user") User user, Model model, @ModelAttribute("recipe") Recipe recipe) {
        List<Recipe> recipeList = recipeRepository.findByTitleLike(query);
        model.addAttribute("recipeList", recipeList);
        return "homeNotSignedIn";
    }

    @RequestMapping({"/rate/{id}/{rating}"})
    public String rate(@PathVariable("rating") int rating, @PathVariable("id") Long recipeId, @ModelAttribute("LoginDto") LoginDto loginDto, Model model) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe not found"));
        User userEntity = userRepository.findOneByUsername(loginDto.getUsername());

        if (userEntity != null && userEntity.getPassword().equals(loginDto.getPassword())) {
            Rating ratingEntity = ratingRepository.findOneByUserAndRecipe(userEntity, recipe);
            if (ratingEntity == null) {
                ratingEntity = new Rating(userEntity, recipe, rating);
            }
            else{
                ratingEntity.setRating(rating);
            }

            ratingRepository.save(ratingEntity);

            return "redirect:/recipes/readRecipe/" + recipeId;
        }

        return "redirect:/login";

    }


}

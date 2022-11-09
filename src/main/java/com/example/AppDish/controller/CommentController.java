package com.example.AppDish.controller;

import com.example.AppDish.dto.CommentDto;
import com.example.AppDish.dto.LoginDto;
import com.example.AppDish.entity.Comment;
import com.example.AppDish.entity.Recipe;
import com.example.AppDish.entity.User;
import com.example.AppDish.repository.CommentRepository;
import com.example.AppDish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@ModelAttribute("CommentDto") CommentDto commentDto, @ModelAttribute("LoginDto") LoginDto loginDto, @PathVariable("commentId") long comment, Model model) {

        Comment deleteComment = commentRepository.findById(comment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found"));
        Long recipeId = deleteComment.getRecipe().getRecipeid();
        com.example.AppDish.entity.User userEntity = userRepository
                .findOneByUsername(loginDto.getUsername());
        System.out.println(loginDto.getUsername());
        if (deleteComment.getUser().getId() == userEntity.getId()) {
            commentRepository.delete(deleteComment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not allowed to delete this comment :)");
        }

        return "redirect:/recipes/readRecipe/" + recipeId;
    }
    @GetMapping({"/changeForm/{userid}/{commentid}"})
    public String updateComment(@PathVariable("userid") Long userid,@ModelAttribute("comment") Comment comment, @PathVariable("commentid") Long commentid, Model muser, Model mrecipe) {
        Optional<Comment> comment1 = commentRepository.findById(commentid); // Get the Recipe
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        if (comment1.isPresent() && currentuser.isPresent()) {
            // Put the Recipe in recipe object, so we can get the information
            comment.setCommentId(comment1.get().getCommentId());
            comment.setRecipe(comment1.get().getRecipe());
            comment.setText(comment1.get().getText());
            comment.setUser(currentuser.get());
            muser.addAttribute("users", currentuser.get());
            return "changeComment";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
    }
    @PostMapping({"/change/{userid}/{commentid}"})
    public String updateComment(@ModelAttribute("comment") Comment comment,@PathVariable("commentid") Long commentid,@ModelAttribute("user") @PathVariable("userid") Long userid, Model model){
        Optional<com.example.AppDish.entity.User> currentuser = userRepository.findById(userid);
        Optional<Comment> comment1 = commentRepository.findById(commentid); // Get the Recipe
        if (comment1.isPresent() && currentuser.isPresent()) {
            Comment comment2=comment1.get();
            comment2.setText(comment.getText());
            commentRepository.save(comment2); // Save the new Recipe with the same RecipeID
            model.addAttribute("users", currentuser.get());
            return  "redirect:/recipes/readRecipe/" + comment2.getRecipe().getRecipeid();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
    }

}



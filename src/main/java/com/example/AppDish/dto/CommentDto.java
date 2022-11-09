package com.example.AppDish.dto;

public class CommentDto {


    private long recipeId;
    private String userId;
    public String password;
    private String comment;

    public long getRecipeId() {
        return recipeId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getComment() {
        return comment;
    }
}

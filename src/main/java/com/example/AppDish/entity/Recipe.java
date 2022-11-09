package com.example.AppDish.entity;


import javax.persistence.*;


@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeId")
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    private String title;
    private String ingredient;
    private String preparation;
    private String photo;
    private int rate_1;
    private int rate_2;
    private int rate_3;
    private int rate_4;
    private int rate_5;


    public Recipe(String title, String preparation, String ingredient) {
        super();
        this.title = title;
        this.preparation = preparation;
        this.ingredient = ingredient;
    }

    public int getRate_1() {
        return rate_1;
    }

    public void setRate_1(int rate_1) {
        this.rate_1 = rate_1;
    }

    public int getRate_2() {
        return rate_2;
    }

    public void setRate_2(int rate_2) {
        this.rate_2 = rate_2;
    }

    public int getRate_3() {
        return rate_3;
    }

    public void setRate_3(int rate_3) {
        this.rate_3 = rate_3;
    }

    public int getRate_4() {
        return rate_4;
    }

    public void setRate_4(int rate_4) {
        this.rate_4 = rate_4;
    }

    public int getRate_5() {
        return rate_5;
    }

    public void setRate_5(int rate_5) {
        this.rate_5 = rate_5;
    }

    public Long getRecipeid() {
        return recipeId;
    }

    public void setRecipeid(Long recipeid) {
        this.recipeId = recipeid;
    }

    public Recipe() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeid=" + recipeId +
                ", user=" + user.toString() +
                ", title='" + title + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", preparation='" + preparation + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

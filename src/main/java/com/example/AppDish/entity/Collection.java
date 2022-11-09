package com.example.AppDish.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collectionId")
    private Long collectionId;

    @Column(name = "name")
    private String collectionName;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;

    public Collection() {
    }

    public Collection(Long collectionId, String collectionName, User user, Set<Recipe> recipes) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.user = user;
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "collectionId=" + collectionId +
                ", collectionName='" + collectionName + '\'' +
                ", user=" + user +
                ", recipes=" + recipes +
                '}';
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}

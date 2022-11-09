package com.example.AppDish.repository;

import com.example.AppDish.entity.Rating;
import com.example.AppDish.entity.Recipe;
import com.example.AppDish.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findOneByUserAndRecipe(User user, Recipe recipe);

    List<Rating> findByRecipe(Recipe recipe);

}

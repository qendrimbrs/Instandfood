package com.example.AppDish.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeTest {

    private static Recipe recipe = new Recipe("cake","he needs some milk", "egg, milk, sugar, salt, oil");

    /*
    public static List<Arguments> recipes() {
        ArrayList<Arguments> recipes = new ArrayList<>();
        recipes.add(Arguments.arguments("Recipe{ userid=null, title = 'cake', preparation = 'he needs some milk'}", recipe1));
        return recipes;
    }

    @ParameterizedTest
    @MethodSource("recipes")
    void testToString(String out, Recipe recipe) {
        assertEquals(out,recipe.toString());
    }
     */

    // Test getTitle
    public static List<Arguments> getTitle() {
        ArrayList<Arguments> recips = new ArrayList<>();
        recips.add(Arguments.arguments("cake", recipe));
        return recips;
    }
    @ParameterizedTest
    @MethodSource("getTitle")
    void testGetFirstName(String out, Recipe recipe) {
        assertEquals(out, recipe.getTitle());
    }

    // Test getPreparation
    public static List<Arguments> getPreparation() {
        ArrayList<Arguments> recips = new ArrayList<>();
        recips.add(Arguments.arguments("he needs some milk", recipe));
        return recips;
    }
    @ParameterizedTest
    @MethodSource("getPreparation")
    void testGetPreparation(String out, Recipe recipe) {
        assertEquals(out, recipe.getPreparation());
    }

    // Test getIngredient
    public static List<Arguments> getIngredient() {
        ArrayList<Arguments> recips = new ArrayList<>();
        recips.add(Arguments.arguments("egg, milk, sugar, salt, oil", recipe));
        return recips;
    }
    @ParameterizedTest
    @MethodSource("getIngredient")
    void testgetIngredient(String out, Recipe recipe) {
        assertEquals(out, recipe.getIngredient());
    }
}

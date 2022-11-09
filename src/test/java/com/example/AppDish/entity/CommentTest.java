package com.example.AppDish.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentTest {

    private static User user = new User("Mmm", "Mxx", "m@outlook.com", "mx", "123456");
    private static Recipe recipe = new Recipe("cake","he needs some milk", "egg, milk, sugar, salt, oil");
    private static Comment comment = new Comment(user, recipe, "This is my Comment");

    // Test getText
    public static List<Arguments> getText() {
        ArrayList<Arguments> comments = new ArrayList<>();
        comments.add(Arguments.arguments("This is my Comment", comment));
        return comments;
    }
    @ParameterizedTest
    @MethodSource("getText")
    void testGetText(String out, Comment comments) {
        assertEquals(out, comments.getText());
    }


    /*
    // Test getUser
    public static List<Arguments> getUser() {
        ArrayList<Arguments> comments = new ArrayList<>();
        comments.add(Arguments.arguments(comment.getUser().getFirstname(), "Mmm"));
        return comments;
    }
    @ParameterizedTest
    @MethodSource("getUser")
    void getUser(String out, Comment comments) {
        assertEquals(out, comments.getUser().getFirstname());
    }

     */


}

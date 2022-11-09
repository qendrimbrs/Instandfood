package com.example.AppDish.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTest {

    private static User user1 = new User("Mmm", "Mxx", "m@outlook.com", "mx", "123456");
    private static User user2 = new User("ggg", "gxx", "g@outlook.com", "gx", "12345");

    // test toString
    public static List<Arguments> users() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("User{userid=null, firstname='Mmm', lastname='Mxx', email='m@outlook.com', username='mx', password='123456'}", user1));
        users.add(Arguments.arguments("User{userid=null, firstname='ggg', lastname='gxx', email='g@outlook.com', username='gx', password='12345'}", user2));

        return users;
    }
    @ParameterizedTest
    @MethodSource("users")
    void testToString(String out, User user) {
        assertEquals(out,user.toString());
    }

    // Test getFirstName
    public static List<Arguments> getFirstName() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("Mmm", user1));
        users.add(Arguments.arguments("ggg", user2));
        return users;
    }
    @ParameterizedTest
    @MethodSource("getFirstName")
    void testGetFirstName(String out, User user) {
        assertEquals(out, user.getFirstname());
    }

    // Test getLastName
    public static List<Arguments> getLastName() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("Mxx", user1));
        users.add(Arguments.arguments("gxx", user2));
        return users;
    }
    @ParameterizedTest
    @MethodSource("getLastName")
    void testGetLastName(String out, User user) {
        assertEquals(out, user.getLastname());
    }

    // Test getEmail
    public static List<Arguments> getEmail() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("m@outlook.com", user1));
        users.add(Arguments.arguments("g@outlook.com", user2));
        return users;
    }
    @ParameterizedTest
    @MethodSource("getEmail")
    void testGetEmail(String out, User user) {
        assertEquals(out, user.getEmail());
    }

    // Test getUsername
    public static List<Arguments> getUserName() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("mx", user1));
        users.add(Arguments.arguments("gx", user2));
        return users;
    }
    @ParameterizedTest
    @MethodSource("getUserName")
    void testGetUserName(String out, User user) {
        assertEquals(out, user.getUsername());
    }

    // Test getPassword
    public static List<Arguments> getPassword() {
        ArrayList<Arguments> users = new ArrayList<>();
        users.add(Arguments.arguments("123456", user1));
        users.add(Arguments.arguments("12345", user2));
        return users;
    }
    @ParameterizedTest
    @MethodSource("getPassword")
    void testGetPassword(String out, User user) {
        assertEquals(out, user.getPassword());
    }


}

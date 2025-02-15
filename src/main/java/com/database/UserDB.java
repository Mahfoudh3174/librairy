package com.database;

import java.util.HashMap;
import java.util.Map;

import com.model.User;

public class UserDB {
    private static Map<String, User> users = new HashMap<>();

    static {
        // Default Admin User
        User admin = new User("admin", "admin", "admin");
        users.put(admin.getId(), admin);
    }

    public static boolean addUser(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static Map<String, User> getBib() {
        Map<String, User> bibs = new HashMap<>();
        for (User u : users.values()) {
            if ("bibliothecaire".equals(u.getRole())) {
                bibs.put(u.getId(), u);
            }
        }
        return bibs;
    }

    public static User findUser(String nom, String password) {
        return users.values().stream()
                .filter(user -> user.getNom().equals(nom) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public static boolean validateUser(String nom, String password) {
        User user = findUser(nom, password);
        return user != null;
    }

    public static void deleteUser(String id) {
        users.remove(id);
    }

    public static User findUserById(String id) {
        return users.get(id);
    }

    public static boolean isUser(String id) {
        return users.containsKey(id);
    }

    public static void editPass(String password) {
        User admin = users.values().stream()
                .filter(user -> "admin".equals(user.getRole()))
                .findFirst()
                .orElse(null);
        if (admin != null) {
            admin.setPassword(password);
        }
    }

    public static void editUser(User user) {
        users.put(user.getId(), user);
    }
}

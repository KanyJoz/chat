package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.User;

import java.util.List;

public interface UserDAO {
    List<User> findAllUsers();
    void deleteUser(User curr);
    List<User> findUsersByName(String name);
    List<User> findUserByHobbies(String hobby);
}
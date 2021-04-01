package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.User;

import java.util.List;

public interface HobbiesDAO {
    List<String> findAllHobbiesForUser(User user);
    int addHobby(String hobbyString);
    void connectUserHobbies(int userId, int hobbyId);
}

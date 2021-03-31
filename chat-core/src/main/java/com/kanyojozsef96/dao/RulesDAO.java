package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import java.util.List;

public interface RulesDAO {
    List<String> findAllRulesForRoom(Room room);
}

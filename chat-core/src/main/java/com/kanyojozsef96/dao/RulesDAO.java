package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.Room;

import java.util.List;

public interface RulesDAO {
    List<String> findAllRulesForRoom(Room room);
    int addRule(String ruleString);
    void connectRoomRules(int roomId, int ruleId);
}

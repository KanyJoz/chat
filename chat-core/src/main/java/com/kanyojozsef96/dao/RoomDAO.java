package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.Room;

import java.util.List;

public interface RoomDAO {
    List<Room> findAllRooms();
    void deleteRoom(Room room);
}

package com.kanyojozsef96.dao;

import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import java.util.List;

public interface RoomDAO {
    List<Room> findAllRooms();
    void deleteRoom(Room room);
    void deleteRoomById(int rId);
    Room findRoomById(int rId);
    List<User> findAllUsersForRoom(Room room);
    List<Room> findRoomsByName(String roomString);
    List<Room> findRoomsByType(String roomType);
    int addRoom(Room roomToAdd, User user);
    boolean populateRoom(int rId, int uId);
    void deleteConnection(int rId, int Uid);
}

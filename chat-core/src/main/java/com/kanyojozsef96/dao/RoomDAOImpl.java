package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM rooms";
    private static final String DELETE_ROOM = "DELETE FROM rooms WHERE id = ?";

    private static final RoomDAOImpl instance = new RoomDAOImpl();
    private String connectionURL;

    private RoomDAOImpl() {
        this.connectionURL = PropertiesUtil.getUtilPropValue("db.url");
    }

    public static RoomDAOImpl getInstance() { return instance; }

    @Override
    public List<Room> findAllRooms() {
        List<Room> allRooms = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(connectionURL);
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(SELECT_ALL_ROOMS)) {

            while (results.next()){
                Room room = new Room();
                room.setId(results.getInt("id"));
                room.setName(results.getString("name"));
                Room.RoomType roomType = Room.RoomType.values()[results.getInt("roomType") - 1];
                room.setRoomType(roomType);
                allRooms.add(room);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return allRooms;
    }


    @Override
    public void deleteRoom(Room currentRoom) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(DELETE_ROOM)) {

            stmt.setInt(1, currentRoom.getId());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows != 1){
                System.out.println("There are some problems with the user id-s!");
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't delete the user from the database");
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(instance.findAllRooms());
    }
}

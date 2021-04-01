package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomDAOImpl implements RoomDAO {
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM rooms";
    private static final String DELETE_ROOM = "DELETE FROM rooms WHERE id = ?";
    private static final String SELECT_ALL_USERS_FOR_ROOM = "SELECT username, email FROM users, rooms_users" +
            " WHERE users.id = rooms_users.userId" +
            " And rooms_users.roomId = ?";
    private static final String SELECT_ROOM_BY_NAME = "SELECT * FROM rooms WHERE name LIKE ?";

    private static final RoomDAOImpl instance = new RoomDAOImpl();
    private String connectionURL;

    private RoomDAOImpl() {
        this.connectionURL = PropertiesUtil.getUtilPropValue("db.url");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    @Override
    public List<User> findAllUsersForRoom(Room room) {
        List<User> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT_ALL_USERS_FOR_ROOM)) {

            stmt.setInt(1, room.getId());
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                result.add(user);
                // TODO: if more data is needed for the web then fill it
                // TODO: Only username, email are querried
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't find the users somehow in the database!");
            throwables.printStackTrace();
            return null;
        }

        return result;
    }


    @Override
    public List<Room> findRoomsByName(String roomString) {
        List<Room> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT_ROOM_BY_NAME)) {

            stmt.setString(1, "%" + roomString + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Room room = new Room();
                room.setName(rs.getString("name"));
                result.add(room);
                // TODO: if more data is needed for the web then fill it
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't find the users somehow in the database!");
            throwables.printStackTrace();
            return null;
        }

        return result;
    }


    @Override
    public List<Room> findRoomsByType(String roomLikeString) {
        List<Room> result = findAllRooms();
        return result.stream().filter(room -> room.getRoomType().getValue().toLowerCase().contains(roomLikeString.toLowerCase())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(instance.findRoomsByType("lov"));
    }
}

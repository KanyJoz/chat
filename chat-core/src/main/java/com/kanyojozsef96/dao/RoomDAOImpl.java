package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDAOImpl implements RoomDAO {
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM rooms";
    private static final String SELECT_ROOM = "SELECT * FROM rooms WHERE id = ?";
    private static final String DELETE_ROOM = "DELETE FROM rooms WHERE id = ?";
    private static final String SELECT_ALL_USERS_FOR_ROOM = "SELECT id, username, email FROM users, rooms_users" +
            " WHERE users.id = rooms_users.userId" +
            " And rooms_users.roomId = ?";
    private static final String SELECT_ROOM_BY_NAME = "SELECT * FROM rooms WHERE name LIKE ?";
    private static final String ADD_ROOM = "INSERT INTO rooms(name, roomType, userId) VALUES (?, ?, ?)";
    private static final String POPULATE_ROOM = "INSERT INTO rooms_users(roomId, userId) VALUES (?, ?)";
    private static final String DELETE_CONNECTION = "DELETE FROM rooms_users WHERE" +
            " roomId = ? AND userId = ?";

    private static final RoomDAOImpl instance = new RoomDAOImpl();
    private String connectionURL;
    private final RulesDAOImpl rulesDAO = RulesDAOImpl.getInstance();

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
                Room.RoomType roomType = Room.RoomType.values()[results.getInt("roomType")];
                room.setRoomType(roomType);
                room.setUserId(results.getInt("userId"));
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
    public void deleteRoomById(int rId) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(DELETE_ROOM)) {

            stmt.setInt(1, rId);
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
    public Room findRoomById(int rId) {
        try(Connection connection = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = connection.prepareStatement(SELECT_ROOM)) {

            stmt.setInt(1, rId);
            ResultSet results = stmt.executeQuery();

            if (results.next()){
                Room room = new Room();
                room.setId(results.getInt("id"));
                room.setName(results.getString("name"));
                Room.RoomType roomType = Room.RoomType.values()[results.getInt("roomType")];
                room.setRoomType(roomType);
                room.setUserId(results.getInt("userId"));
                return room;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
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
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                result.add(user);
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
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                Room.RoomType roomType = Room.RoomType.values()[rs.getInt("roomType")];
                room.setRoomType(roomType);
                room.setUserId(rs.getInt("userId"));
                result.add(room);
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


    @Override
    public int addRoom(Room roomToAdd, User user) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(ADD_ROOM, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, roomToAdd.getName());
            stmt.setInt(2, roomToAdd.getRoomType().ordinal());
            stmt.setInt(3, user.getId());


            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                System.out.println("Something went wrong with the insert");
                return -1;
            }


            ResultSet keys = stmt.getGeneratedKeys();
            int key;

            if(keys.next()){
                key = keys.getInt(1);
            } else {
                System.out.println("Shouldn't reach this");
                return -1;
            }

            roomToAdd.getRules().forEach(rule -> {
                int ruleKey = rulesDAO.addRule(rule);
                if(ruleKey != -1) {
                    rulesDAO.connectRoomRules(key, ruleKey);
                }
            });

            return key;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }


    @Override
    public boolean populateRoom(int rId, int uId) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(POPULATE_ROOM)) {

            stmt.setInt(1, rId);
            stmt.setInt(2, uId);


            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                System.out.println("Something went wrong with the insert");
                return false;
            }

            return true;

        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public void deleteConnection(int rId, int Uid) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(DELETE_CONNECTION)) {

            stmt.setInt(1, rId);
            stmt.setInt(2, Uid);
            int affectedRows = stmt.executeUpdate();

            if(affectedRows != 1){
                System.out.println("There are some problems with the user id-s!");
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't delete the user from the database");
        }
    }

    public static void main(String[] args) {
        System.out.println(instance.findRoomsByType("lov"));
    }
}

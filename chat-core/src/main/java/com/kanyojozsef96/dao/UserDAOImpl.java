package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.User;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USERS_BY_NAME = "SELECT * FROM users WHERE username LIKE ?";
    private static final String SELECT_USERS_BY_HOBBY = "SELECT users.username, users.email FROM users, hobbies, users_hobbies" +
            " WHERE users.id = users_hobbies.userId" +
            " AND hobbies.id = users_hobbies.hobbyId" +
            " AND hobbies.id IN" +
            " (SELECT id FROM hobbies" +
            " WHERE name LIKE ?)";
    private static final String ADD_USER = "INSERT INTO users(username, password, email, age, sex)" +
            " VALUES (?, ?, ?, ?, ?)";


    /*
     SELECT users.username, users.email FROM users, hobbies, users_hobbies
     WHERE users.id = users_hobbies.userId
       AND hobbies.id = users_hobbies.hobbyId
       AND hobbies.id IN
     (SELECT id FROM hobbies
       WHERE name LIKE '%?%')
    * */

    private static final UserDAOImpl instance = new UserDAOImpl();
    private final String connectionUrl;
    private final HobbiesDAOImpl hobbiesDAO = HobbiesDAOImpl.getInstance();

    private UserDAOImpl() {
        this.connectionUrl = PropertiesUtil.getUtilPropValue("db.url");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UserDAOImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(SELECT_ALL_USERS)) {

            while (results.next()){
                User user = new User();
                user.setId(results.getInt("id"));
                user.setUsername(results.getString("username"));
                user.setEmail(results.getString("email"));
                allUsers.add(user);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return allUsers;
    }


    @Override
    public void deleteUser(User curr) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(DELETE_USER)) {

            stmt.setInt(1, curr.getId());
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
    public List<User> findUsersByName(String likeString) {
        List<User> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(SELECT_USERS_BY_NAME)) {

            stmt.setString(1, "%" + likeString + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                result.add(user);
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
    public List<User> findUserByHobbies(String hobbyString) {
        List<User> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(SELECT_USERS_BY_HOBBY)) {

            stmt.setString(1, "%" + hobbyString + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                result.add(user);
                // TODO: if more data is needed for the web then fill it
                // TODO: ALSO this only queries user name and email
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't find the users somehow in the database!");
            throwables.printStackTrace();
            return null;
        }

        return result;
    }


    @Override
    public boolean addUser(User newUser) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.getEmail());
            stmt.setInt(4, newUser.getAge());
            stmt.setString(5, newUser.getSex());


            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                System.out.println("Something went wrong with the insert");
                return false;
            }


            ResultSet keys = stmt.getGeneratedKeys();
            int key;

            if(keys.next()){
                key = keys.getInt(1);
            } else {
                System.out.println("Shouldn't reach this");
                return false;
            }

            newUser.getHobbies().forEach(hobby -> {
                int hobbyKey = hobbiesDAO.addHobby(hobby);
                if(hobbyKey != -1) {
                    hobbiesDAO.connectUserHobbies(key, hobbyKey);
                }
            });

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        User tmp1 = new User();
        tmp1.setUsername("kanfffffyJoz");
        tmp1.setEmail("ffffff");
        tmp1.setPassword("pwd");
        tmp1.setAge(44);
        tmp1.setSex("male");
        tmp1.setHobbies(FXCollections.observableArrayList("bicaj", "futas"));

        instance.addUser(tmp1);
    }
}



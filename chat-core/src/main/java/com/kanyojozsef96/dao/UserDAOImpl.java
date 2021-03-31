package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";

    private static final UserDAOImpl instance = new UserDAOImpl();
    private final String connectionUrl;

    private UserDAOImpl() {
        this.connectionUrl = PropertiesUtil.getUtilPropValue("db.url");
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
}

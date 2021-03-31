package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HobbiesDAOImpl implements HobbiesDAO {
    private static final String SELECT_ALL_HOBBIES = "SELECT name FROM users, hobbies, users_hobbies" +
            " WHERE users.id = users_hobbies.userId" +
            " AND hobbies.id = users_hobbies.hobbyId" +
            " AND users.id = ?";

    private static final HobbiesDAOImpl instance = new HobbiesDAOImpl();
    private String connectionURL;

    private HobbiesDAOImpl() {
        this.connectionURL = PropertiesUtil.getUtilPropValue("db.url");
    }

    public static HobbiesDAOImpl getInstance() { return instance; }

    @Override
    public List<String> findAllHobbiesForUser(User currentUser) {
        List<String> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT_ALL_HOBBIES)) {

            stmt.setInt(1, currentUser.getId());

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String tmp = rs.getString("name");
                result.add(tmp);
            }

        } catch (SQLException throwables) {
            System.out.println("Couldn't connect to database!");
            throwables.printStackTrace();
            return null;
        }

        return result;
    }
}

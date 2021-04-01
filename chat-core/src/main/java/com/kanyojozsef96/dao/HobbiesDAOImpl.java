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
    private static final String ADD_HOBBY = "INSERT INTO hobbies(name) VALUES (?)";
    private static final String SELECT_HOBBY_ID = "SELECT id FROM hobbies WHERE name = ?";
    private static final String ADD_USER_HOBBY = "INSERT INTO users_hobbies(userId, hobbyId) VALUES (?, ?)";

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


    @Override
    public int addHobby(String hobbyString) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(ADD_HOBBY, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt2 = c.prepareStatement(SELECT_HOBBY_ID)) {

            stmt2.setString(1, hobbyString);
            ResultSet hobId = stmt2.executeQuery();
            if(hobId.next()){
                return hobId.getInt("id");
            }


            stmt.setString(1, hobbyString);
            int affectedRows = stmt.executeUpdate();
            if(affectedRows != 1) {
                System.out.println("Something went wrong with the insertion of the new hobby");
                return -1;
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public void connectUserHobbies(int userId, int hobbyId) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(ADD_USER_HOBBY)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, hobbyId);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows != 1) {
                System.out.println("Something went wrong with the adding of a user hobby pair");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

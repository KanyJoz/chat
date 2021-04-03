package com.kanyojozsef96.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.Conversation;
import com.kanyojozsef96.model.User;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USERS_BY_NAME = "SELECT * FROM users WHERE username LIKE ?";
    private static final String SELECT_USERS_BY_HOBBY = "SELECT users.id, users.username, users.email FROM users, hobbies, users_hobbies" +
            " WHERE users.id = users_hobbies.userId" +
            " AND hobbies.id = users_hobbies.hobbyId" +
            " AND hobbies.id IN" +
            " (SELECT id FROM hobbies" +
            " WHERE name LIKE ?)";
    private static final String ADD_USER = "INSERT INTO users(username, password, email, age, sex)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_USER = "SELECT * FROM users WHERE username = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String LIST_MESSAGES = "SELECT * FROM (SELECT * FROM users_conversations" +
            " WHERE loginUserId = ? AND otherUserId = ?" +
            " ORDER BY conversationId DESC LIMIT 10)" +
            " ORDER BY conversationId ASC";
    private static final String ADD_MESSAGE = "INSERT INTO users_conversations(loginUserId, otherUserId, message, sender)" +
            " VALUES(?, ?, ?, ?)";


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
    public List<User> findUserByHobbies(String hobbyString) {
        List<User> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(SELECT_USERS_BY_HOBBY)) {

            stmt.setString(1, "%" + hobbyString + "%");
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
    public boolean addUser(User newUser) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {

            String newPwd = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());
            newUser.setPassword(newPwd);

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
                newUser.setId(key);
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


    @Override
    public User findUserByNameAndPwd(String n, String pwd) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(FIND_USER)) {

            stmt.setString(1, n);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String dbPass = rs.getString("password");
                BCrypt.Result result = BCrypt.verifyer().verify(pwd.toCharArray(), dbPass);

                if(result.verified) {
                    User user1 = new User();
                    user1.setId(rs.getInt("id"));
                    user1.setUsername(rs.getString("username"));
                    user1.setPassword(rs.getString("password"));
                    user1.setEmail(rs.getString("email"));
                    user1.setAge(rs.getInt("age"));
                    user1.setSex(rs.getString("sex"));
                    user1.setHobbies(FXCollections.observableArrayList(hobbiesDAO.findAllHobbiesForUser(user1)));
                    return user1;
                }
            } else {
                System.out.println("Someting went wrong finding the user");
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }


    @Override
    public User findUserById(int UID) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(FIND_USER_BY_ID)) {

            stmt.setInt(1, UID);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                User user1 = new User();
                user1.setId(rs.getInt("id"));
                user1.setUsername(rs.getString("username"));
                user1.setPassword(rs.getString("password"));
                user1.setEmail(rs.getString("email"));
                user1.setAge(rs.getInt("age"));
                user1.setSex(rs.getString("sex"));
                return user1;
            } else {
                System.out.println("Someting went wrong finding the user");
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Conversation> listMessages(int loginUID, int otherUID) {
        List<Conversation> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(LIST_MESSAGES)) {


            stmt.setInt(1, loginUID);
            stmt.setInt(2, otherUID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Conversation convo = new Conversation();
                convo.setConversationId(rs.getInt("conversationId"));
                convo.setLoginUserId(rs.getInt("loginUserId"));
                convo.setOtherUserId(rs.getInt("otherUserId"));
                convo.setMessage(rs.getString("message"));
                convo.setSender(rs.getString("sender"));
                result.add(convo);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return result;
    }


    @Override
    public boolean addMessage(int loginUID, int otherUID, String message, boolean first) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(ADD_MESSAGE)) {

            stmt.setInt(1, loginUID);
            stmt.setInt(2, otherUID);
            stmt.setString(3, message);

            User tmp;
            if(first) {
                tmp = findUserById(loginUID);
            } else {
                tmp = findUserById(otherUID);
            }
            stmt.setString(4, tmp.getUsername());


            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                System.out.println("Something went wrong with the insert");
                return false;
            }

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(instance.listMessages(9, 1));
    }
}



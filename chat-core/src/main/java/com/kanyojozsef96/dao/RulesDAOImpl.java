package com.kanyojozsef96.dao;

import com.kanyojozsef96.config.PropertiesUtil;
import com.kanyojozsef96.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RulesDAOImpl implements RulesDAO {
    private static final String SELECT_ALL_RULES = "SELECT rules.name FROM rooms, rules, rooms_rules" +
            " WHERE rooms.id = rooms_rules.roomId" +
            " AND rules.id = rooms_rules.ruleId" +
            " AND rooms.id = ?";
    private static final String ADD_RULE = "INSERT INTO rules(name) VALUES (?)";
    private static final String SELECT_RULE_ID = "SELECT id FROM rules WHERE name = ?";
    private static final String ADD_ROOM_RULE = "INSERT INTO rooms_rules(roomId, ruleId) VALUES (?, ?)";

    private static final RulesDAOImpl instance = new RulesDAOImpl();
    private String connectionURL;

    private RulesDAOImpl() {
        this.connectionURL = PropertiesUtil.getUtilPropValue("db.url");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static RulesDAOImpl getInstance() { return instance; }

    @Override
    public List<String> findAllRulesForRoom(Room currentRoom) {
        List<String> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT_ALL_RULES)) {

            stmt.setInt(1, currentRoom.getId());

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
    public int addRule(String ruleString) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(ADD_RULE, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt2 = c.prepareStatement(SELECT_RULE_ID)) {

            stmt2.setString(1, ruleString);
            ResultSet hobId = stmt2.executeQuery();
            if(hobId.next()){
                return hobId.getInt("id");
            }


            stmt.setString(1, ruleString);
            int affectedRows = stmt.executeUpdate();
            if(affectedRows != 1) {
                System.out.println("Something went wrong with the insertion of the new rule");
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
    public void connectRoomRules(int roomId, int ruleId) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(ADD_ROOM_RULE)) {

            stmt.setInt(1, roomId);
            stmt.setInt(2, ruleId);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows != 1) {
                System.out.println("Something went wrong with the adding of a room rule pair");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

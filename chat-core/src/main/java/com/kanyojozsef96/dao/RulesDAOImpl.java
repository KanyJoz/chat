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
}

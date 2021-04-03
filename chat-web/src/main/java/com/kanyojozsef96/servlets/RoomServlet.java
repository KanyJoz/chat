package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.dao.RulesDAO;
import com.kanyojozsef96.dao.RulesDAOImpl;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;
import javafx.collections.ObservableList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/room-servlet")
public class RoomServlet extends HttpServlet {
    private final RoomDAOImpl roomDAO = RoomDAOImpl.getInstance();
    private final RulesDAO rulesDAO = RulesDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/room.jsp";
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        Room room = roomDAO.findRoomById(roomId);
        req.getSession().setAttribute("room", room);

        List<String> rules = rulesDAO.findAllRulesForRoom(room);
        String tmpRules = "";
        for(int i = 0; i < rules.size(); ++i) {
            tmpRules += rules.get(i);
            if(i != rules.size() - 1){
                tmpRules += System.lineSeparator();
            }
        }
        req.getSession().setAttribute("tmpRules", tmpRules);

        resp.sendRedirect(req.getContextPath() + url);
    }
}

package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;
import javafx.collections.FXCollections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newroom-servlet")
public class NewRoomServlet extends HttpServlet {
    private final RoomDAOImpl roomDao = RoomDAOImpl.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("roomname");
        int roomType = Integer.parseInt(req.getParameter("roomtype"));
        String rules = req.getParameter("rules");

        String url;

        Room room = new Room();
        room.setName(name);
        room.setRoomType(Room.RoomType.values()[roomType-1]);
        room.setRules(FXCollections.observableArrayList(rules.split("\n")));


        if(roomDao.addRoom(room)) {
            url = "/index.jsp";
        } else {
            url = "/error.jsp";
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

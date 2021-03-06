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
import java.util.List;

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
        room.setRoomType(Room.RoomType.values()[roomType]);
        room.setRules(FXCollections.observableArrayList(rules.split(System.lineSeparator())));


        if(req.getSession().getAttribute("user") != null) {
            int key = roomDao.addRoom(room, ((User)req.getSession().getAttribute("user")));
            room.setId(key);
            if(key != -1) {
                User u = (User) req.getSession().getAttribute("user");
                roomDao.populateRoom(room.getId(), u.getId());
                url = "/index.jsp";
            } else {
                url = "/error.jsp";
                req.getSession().setAttribute("error", "This room already exists, try another name");
            }
        } else {
            url = "/error.jsp";
            req.getSession().setAttribute("error", "You have to be logged in in order to create rooms");
        }


        resp.sendRedirect(req.getContextPath() + url);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            int roomId = Integer.parseInt(req.getParameter("roomId"));
            User u = (User) req.getSession().getAttribute("user");
            Room r = roomDao.findRoomById(roomId);
            if(u.getId() == r.getUserId()) {
                roomDao.deleteRoomById(roomId);
                url = "/index.jsp";
            } else {
                req.getSession().setAttribute("error", "Only the creator can delete the room!");
            }
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to delete rooms");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/roomtype-servlet")
public class RoomTypeServlet extends HttpServlet {
    private final RoomDAOImpl roomDAO = RoomDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            int roomtype = Integer.parseInt(req.getParameter("roomtype"));
            List<Room> rooms = roomDAO.findRoomsByType(Room.RoomType.values()[roomtype -1].getValue());
            req.getSession().setAttribute("rooms", rooms);
            url = "/roomList.jsp";
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to search for rooms");

        }

        resp.sendRedirect(req.getContextPath() + url);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            int roomId = Integer.parseInt(req.getParameter("roomId"));
            roomDAO.populateRoom( roomId, ((User)req.getSession().getAttribute("user")).getId());

            Room r = new Room();
            r.setId(roomId);
            List<User> users = roomDAO.findAllUsersForRoom(r);
            req.getSession().setAttribute("users", users);
            url = "/userList.jsp";
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to list users");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

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

@WebServlet("/roomname-servlet")
public class RoomnameServlet extends HttpServlet {
    private final RoomDAOImpl roomDAO = RoomDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            String roomname = req.getParameter("roomname");
            List<Room> rooms = roomDAO.findRoomsByName(roomname);
            req.getSession().setAttribute("rooms", rooms);
            url = "/roomList.jsp";
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}
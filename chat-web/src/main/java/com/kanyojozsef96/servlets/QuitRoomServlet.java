package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.RoomDAO;
import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/quitroom-servlet")
public class QuitRoomServlet extends HttpServlet {
    private final RoomDAO roomDao = RoomDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("roomId"));
        User user = (User) req.getSession().getAttribute("user");
        roomDao.deleteConnection(roomID, user.getId());
        String url = "/index.jsp";
        resp.sendRedirect(req.getContextPath() + url);
    }
}

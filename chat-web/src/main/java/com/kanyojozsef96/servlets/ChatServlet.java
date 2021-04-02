package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/chat-servlet")
public class ChatServlet extends HttpServlet {
    private final UserDAOImpl userDao = UserDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            String otherUID = req.getParameter("otherUserId");
            List<String> messages = userDao.listMessages(((User)req.getSession().getAttribute("user")).getId(), Integer.parseInt(otherUID));
            req.getSession().setAttribute("messages", messages);
            url = "/chat.jsp";
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

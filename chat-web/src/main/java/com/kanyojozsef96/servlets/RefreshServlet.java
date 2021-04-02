package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.Conversation;
import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/refresh-servlet")
public class RefreshServlet extends HttpServlet {
    private final UserDAOImpl userDao = UserDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            List<Conversation> messages = userDao.listMessages(((User)req.getSession().getAttribute("user")).getId(), Integer.parseInt((String)req.getSession().getAttribute("otherUserId")));
            req.getSession().setAttribute("messages", messages);
            url = "/chat.jsp";
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to chat with others");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

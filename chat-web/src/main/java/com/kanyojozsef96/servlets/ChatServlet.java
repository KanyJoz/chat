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

@WebServlet("/chat-servlet")
public class ChatServlet extends HttpServlet {
    private final UserDAOImpl userDao = UserDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            String otherUID = req.getParameter("otherUserId");
            System.out.println(otherUID);

            if(((User)req.getSession().getAttribute("user")).getId() == Integer.parseInt(otherUID)) {
                req.getSession().setAttribute("error", "You can't have a conversation with yourself");
            } else {
                req.getSession().setAttribute("otherUserId", otherUID);
                List<Conversation> messages = userDao.listMessages(((User)req.getSession().getAttribute("user")).getId(), Integer.parseInt(otherUID));
                req.getSession().setAttribute("messages", messages);
                url = "/chat.jsp";
            }
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to chat with others");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            String otherUID = (String) req.getSession().getAttribute("otherUserId");

            userDao.addMessage(((User)req.getSession().getAttribute("user")).getId(),
                    Integer.parseInt(otherUID), req.getParameter("message"), true);
            userDao.addMessage(Integer.parseInt(otherUID), ((User)req.getSession().getAttribute("user")).getId(),
                       req.getParameter("message"), false);

            List<Conversation> messages = userDao.listMessages(((User)req.getSession().getAttribute("user")).getId(), Integer.parseInt(otherUID));

            req.getSession().setAttribute("messages", messages);
            url = "/chat.jsp";
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to chat with others");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

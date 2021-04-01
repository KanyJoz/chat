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

@WebServlet("/hobby-servlet")
public class HobbyServlet extends HttpServlet {
    private final UserDAOImpl userDao = UserDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {

            String hobby = req.getParameter("hobby");
            List<User> users = userDao.findUserByHobbies(hobby);
            req.getSession().setAttribute("users", users);
            url = "/userList.jsp";
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.User;
import javafx.collections.FXCollections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    private final UserDAOImpl userDAO = UserDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String url;

        User user = userDAO.findUserByNameAndPwd(username, password);


        if(user != null) {
            url = "/index.jsp";
            req.getSession().setAttribute("user", user);
        } else {
            url = "/error.jsp";
        }


        resp.sendRedirect(req.getContextPath() + url);
    }
}

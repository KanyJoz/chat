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

@WebServlet("/registration-servlet")
public class RegistrationServlet extends HttpServlet {
    private final UserDAOImpl userDAO = UserDAOImpl.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordAgain = req.getParameter("passwordAgain");
        String email = req.getParameter("email");
        int age = Integer.parseInt(req.getParameter("age"));
        String sex = req.getParameter("sex");
        String hobbies = req.getParameter("hobbies");

        String url;

        if(!password.equals(passwordAgain)){
            url = "/error.jsp";
            req.getSession().setAttribute("error", "The two passwords don't match");
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setAge(age);
            user.setSex(sex);
            user.setHobbies(FXCollections.observableArrayList(hobbies.split("\n")));


            if(userDAO.addUser(user)) {
                url = "/index.jsp";
                req.getSession().setAttribute("user", user);
            } else {
                url = "/error.jsp";
                req.getSession().setAttribute("error", "This user already exists, try another username and/or email address");
            }
        }


         resp.sendRedirect(req.getContextPath() + url);
    }
}

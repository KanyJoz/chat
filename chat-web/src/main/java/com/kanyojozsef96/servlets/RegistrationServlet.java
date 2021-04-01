package com.kanyojozsef96.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration-servlet")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordAgain = req.getParameter("passwordAgain");
        String email = req.getParameter("email");
        int age = Integer.parseInt(req.getParameter("age"));
        String sex = req.getParameter("sex");
        String hobbies = req.getParameter("hobbies");


    }
}

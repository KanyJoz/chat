package com.kanyojozsef96.servlets;

import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout-servlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/index.jsp";
        req.getSession().setAttribute("user", null);
        resp.sendRedirect(req.getContextPath() + url);
    }
}

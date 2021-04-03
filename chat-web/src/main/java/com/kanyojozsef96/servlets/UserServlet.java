package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user-servlet")
public class UserServlet extends HttpServlet {
    private final UserDAOImpl userDAO = UserDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        ObservableList<String> hobbies = user.getHobbies();
        String tmpHobbies = "";
        for(int i = 0; i < hobbies.size(); ++i) {
            tmpHobbies += hobbies.get(i);
            if(i != hobbies.size() - 1){
                tmpHobbies += System.lineSeparator();
            }
        }
        req.getSession().setAttribute("tmpHobbies", tmpHobbies);
        String url = "/user.jsp";
        resp.sendRedirect(req.getContextPath() + url);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/index.jsp";
        User u = (User) req.getSession().getAttribute("user");
        userDAO.deleteUser(u);
        resp.sendRedirect(req.getContextPath() + url);
    }
}

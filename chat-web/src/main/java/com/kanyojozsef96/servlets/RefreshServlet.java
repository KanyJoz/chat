package com.kanyojozsef96.servlets;

import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.Conversation;
import com.kanyojozsef96.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Vector;

@WebServlet("/refresh-servlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 5 * 5 // 25 MB
)
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/error.jsp";
        if(req.getSession().getAttribute("user") != null) {
            Vector<InputStream> allParts = new Vector<>();

            for (Part part : req.getParts()) {
                allParts.add(part.getInputStream());
            }

            SequenceInputStream appended = new SequenceInputStream(allParts.elements());
            byte[] img = appended.readAllBytes();
            String result = Base64.getEncoder().encodeToString(img);



            String otherUID = (String) req.getSession().getAttribute("otherUserId");

            userDao.addMessage(((User)req.getSession().getAttribute("user")).getId(),
                    Integer.parseInt(otherUID), result, true);
            userDao.addMessage(Integer.parseInt(otherUID), ((User)req.getSession().getAttribute("user")).getId(),
                    result, false);

            List<Conversation> messages = userDao.listMessages(((User)req.getSession().getAttribute("user")).getId(), Integer.parseInt(otherUID));

            req.getSession().setAttribute("messages", messages);
            url = "/chat.jsp";
        } else {
            req.getSession().setAttribute("error", "You have to be logged in in order to send images");
        }

        resp.sendRedirect(req.getContextPath() + url);
    }
}

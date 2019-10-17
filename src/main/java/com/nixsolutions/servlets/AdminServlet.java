package com.nixsolutions.servlets;

import com.nixsolutions.dao.JdbcUserDao;
import com.nixsolutions.entity.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user_name", req.getSession(false).getAttribute("auth_admin"));
        JdbcUserDao userDao = new JdbcUserDao();
        List<User> users = userDao.findAll();
        req.setAttribute("users_list", users);
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JdbcUserDao userDao = new JdbcUserDao();
        List<User> users = userDao.findAll();
        req.setAttribute("users_list", users);
    }
}

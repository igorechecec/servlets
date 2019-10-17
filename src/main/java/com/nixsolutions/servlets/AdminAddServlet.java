package com.nixsolutions.servlets;

import com.nixsolutions.dao.JdbcRoleDao;
import com.nixsolutions.dao.JdbcUserDao;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/add")
public class AdminAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user_name", req.getSession(false).getAttribute("auth_admin"));
        req.getRequestDispatcher("WEB-INF/jsp/add_user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = null;
        RoleDao roleDao = new JdbcRoleDao();
        UserDao userDao = new JdbcUserDao();
        if (!req.getParameter("password").equals(req.getParameter("password-again"))) {
            session = req.getSession(false);
            session.setAttribute("message", "You passed different password!");
            resp.sendRedirect("WEB-INF/error.jsp");
            return;
        }
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setBirthday(new Date(date.getTime()));
        user.setRole(roleDao.findByName(req.getParameter("role")));

        userDao.create(user);
        resp.sendRedirect("/admin");
    }
}
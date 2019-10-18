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
import org.apache.commons.lang3.StringUtils;

@WebServlet("/edit")
public class AdminEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JdbcUserDao userDao = new JdbcUserDao();
        if (req.getParameter("id") != null) {
            Long id = Long.valueOf(req.getParameter("id"));
            User user = userDao.findById(id);
            if (user != null) {
                int i = user.getRole().getName().equals("Admin") ? 1 : 0;
                req.setAttribute("user_name", req.getSession(false).getAttribute("auth_admin"));
                req.setAttribute("role_id", i);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/jsp/edit_user.jsp").forward(req, resp);
            } else {
                req.getSession(false).setAttribute("error", "User is not exists!");
                resp.sendRedirect("/error");
            }
        } else {
            resp.sendRedirect("/admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkForm(req)) {
            resp.sendRedirect("/error");
            return;
        }
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
        user.setId(Long.parseLong(req.getParameter("user_id")));
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

        userDao.update(user);
        resp.sendRedirect("/admin");
    }

    /**
     * Method checks input from user.
     *
     * @param req request
     * @return
     */
    private boolean checkForm(HttpServletRequest req) {
        String[] formParams = {"login", "password", "email", "firstname",
            "lastname", "birthday", "role"};
        for (String param: formParams) {
            if (StringUtils.isEmpty(req.getParameter(param))) {
                req.getSession(false).setAttribute("error",
                    "Field " + param + " should be define");
                return false;
            }
        }
        return true;
    }
}

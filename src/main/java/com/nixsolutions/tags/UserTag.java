package com.nixsolutions.tags;

import com.nixsolutions.entity.User;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.joda.time.Years;
import org.joda.time.LocalDate;

public class UserTag extends SimpleTagSupport {

    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder tag = new StringBuilder("<div class=\"container\">\n" +
            "<table class=\"table table-striped\">\n" +
            "<thead><tr><th>Login</th><th>First Name</th>" +
            "<th>Last Name</th><th>Age</th><th>Role</th>" +
            "<th>Action</th></tr>" +
            "</thead><tbody>");

        for (User user: users) {
            tag.append("<tr><td class=\"align-middle\">" + user.getLogin() + "</td>")
            .append("<td class=\"align-middle\">" + user.getFirstName() + "</td>")
            .append("<td class=\"align-middle\">" + user.getLastName() + "</td>")
            .append("<td class=\"align-middle\">" + calculateAge(user.getBirthday()) + "</td>")
            .append("<td class=\"align-middle\">" + user.getRole().getName() + "</td>")
            .append("<td  class=\"align-middle\"><form action=\"admin?delete=\" " + user.getLogin() + "\" onSubmit=\"return confirm('Are you sure?')\"><a href=\"admin?edit=" + user.getLogin()+ "\" class=\"btn btn-primary\">Edit</a>")
            .append("<button type=\"submit\" class=\"btn btn-danger delete_button\" " +
                "value=\"delete\">Delete</button></form>")
            .append("</td></tr>");
        }

        tag.append("</tbody></table></div>");
        JspWriter out = getJspContext().getOut();
        out.println(tag);
    }

    private int calculateAge(Date birthday) {
        LocalDate birthDate = new LocalDate(new java.util.Date(birthday.getTime()));
        return Years.yearsBetween(birthDate, LocalDate.now()).getYears();
    }
}

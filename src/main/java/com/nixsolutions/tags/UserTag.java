package com.nixsolutions.tags;

import com.nixsolutions.entity.User;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.joda.time.DateTime;
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
            .append("<td><a href=\"admin?edit=" + user.getId()+ "\" class=\"btn btn-primary\">Edit</a>")
            .append("<button type=\"button\" class=\"btn btn-danger delete_button\" " +
                "data-toggle=\"modal\" data-target=\"#deleteModal\">Delete</button>")
//            tag.append("<div class=\"modal fade\" id=\"deleteModal\" " +
//                "tabindex=\"-1\" role=\"dialog\" " +
//                "aria-labelledby=\"exampleModalCenterTitle\" " +
//                "aria-hidden=\"true\">")
//            .append("<div class=\"modal-content\"><div class=\"modal-header\">")
//            .append("<h5 class=\"modal-title\" id=\"exampleModalCenterTitle\">Confirm</h5>")
//            .append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">")
//            .append("<span aria-hidden=\"true\">&times;</span></button></div>")
//            .append("<div class=\"modal-body\">")
//            .append("<span>Are you sure</span>")
//            .append("</div><div class=\"modal-footer\">")
//            .append("<button type=\"button\" class=\"btn btn-secondary\" " +
//                "data-dismiss=\"modal\">Close</button>")
//            .append("<a href=\"admin?delete=" + user.getId()+ "\" " +
//                "type=\"button\" class=\"btn btn-primary\">Delete</button>")
//            .append("</div></div></div></div>")
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

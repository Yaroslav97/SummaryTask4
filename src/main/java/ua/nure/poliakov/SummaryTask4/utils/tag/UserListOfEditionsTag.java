package ua.nure.poliakov.SummaryTask4.utils.tag;

import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.List;

public class UserListOfEditionsTag extends BodyTagSupport {

    private List<Edition> list;

    public void setList(List<Edition> list) {
        this.list = list;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = null;
        try {
            out = pageContext.getOut();
            out.write("<table class=\"table\">");
            out.write("<tr>");
            out.write("<th>" + "name" + "</th>");
            out.write("<th>" + "subject" + "</th>");
            out.write("<th>" + "price" + "</th>");
            out.write("</tr>");
            for (Edition edition : list) {
                out.write("<tr>");
                out.write("<td>" + edition.getName() + "</td>");
                out.write("<td>" + edition.getSubject() + "</td>");
                out.write("<td>" + edition.getPrice() + "</td>");
                out.write("</tr>");
            }
            out.write("<table/>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}

package ru.geekbrains;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/http_servlet/*")
public class HttpServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();


        pw.println("<p>getContextPath "+req.getContextPath()+"</p>");
        pw.println("<p>getServletPath "+req.getServletPath()+"</p>");
        pw.println("<p>getPathInfo "+req.getPathInfo()+"</p>");
        pw.println("<p>getQueryString "+req.getQueryString()+"</p>");
        pw.println("<p>getParameter1 "+req.getParameter("param1")+"</p>");
        pw.println("<p>getParameter2 "+req.getParameter("param2")+"</p>");
    }
}

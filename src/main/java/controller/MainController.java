package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String pass = request.getParameter("pass");
        String login = request.getParameter("login");
        if (login.equals("TestTask") && pass.equals("TestDev20191")) {
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

    }
}

package controller;
import dao.DAO;
import model.Order;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class Controller extends HttpServlet  {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

            ArrayList<Order> orders = DAO.getAll();
                request.setAttribute("orders", orders);
               request.getRequestDispatcher("/data.jsp").forward(request, response);
    }
}
package controllers;

import Bolbolestan.Bolbolestan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.util.logging.Logger;


@WebServlet(name = "HomeController", urlPatterns = "")
public class HomeController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in controller");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            System.out.println("something\n\n\n"); // --------------------------------
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.out.println("no one found");
            response.sendRedirect("http://localhost:8080/ca3_war_exploded/login");
        }
    }
}
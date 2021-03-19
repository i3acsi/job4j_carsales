package ru.job4j.carsales.servlet;

import ru.job4j.carsales.model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "")
public class GreetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("user");
        String role = (String) session.getAttribute("role");
        if (account != null) {
            req.setAttribute("user", account.getName());
            if (role != null) {
                req.setAttribute("role", role);
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

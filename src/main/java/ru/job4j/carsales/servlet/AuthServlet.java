package ru.job4j.carsales.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.repo.Repo;
import ru.job4j.carsales.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);
    private Repo repo;

    @Override
    public void init() throws ServletException {
        this.repo = (Repo) this.getServletContext().getAttribute("repo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/logreg.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (!AuthService.checkAndSetCredentials(email, password, req, repo)) {
                String msg = "wrong credentials: Email: " + email + ", Password: " + password;
                log.error(msg);
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else if ("reg".equals(action)) {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            if (!AuthService.createAccount(name, email, password, phone, repo)) {
                String msg = "can't create a new account with these credentials: Name:" + name + ", Email: " + email + ", Password: " + password;
                log.error(msg);
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
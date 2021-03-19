package ru.job4j.carsales.filter;

import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/editDB.jsp"})
public class AdminFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Account account = AuthService.getLoggedInAccount(req);
        if (!AuthService.isAdmin(account)) {
            res.sendRedirect(req.getContextPath());
        } else {
            chain.doFilter(req, res);
        }
    }
}

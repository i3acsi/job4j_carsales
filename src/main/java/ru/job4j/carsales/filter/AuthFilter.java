package ru.job4j.carsales.filter;

import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/userInfo", "/car"})
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Account account = AuthService.getLoggedInAccount(req);
        if ("POST".equals(req.getMethod()) && account == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }
        chain.doFilter(req, res);
    }
}
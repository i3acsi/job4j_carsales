package ru.job4j.carsales.filter;

import ru.job4j.carsales.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/add.jsp", "/account.jsp"})
public class UserFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (AuthService.getLoggedInAccount(req) == null) {
            res.sendRedirect(req.getContextPath());
        } else {
            chain.doFilter(req, res);
        }
    }
}

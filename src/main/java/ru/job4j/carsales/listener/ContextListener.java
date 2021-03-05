package ru.job4j.carsales.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carsales.repo.Repo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("repo", new Repo());
        sce.getServletContext().setAttribute("mapper", new ObjectMapper());
    }
}

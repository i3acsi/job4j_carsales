package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carsales.model.Transmission;
import ru.job4j.carsales.repo.Repo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(urlPatterns = "/transmission")
public class TransmissionServlet extends HttpServlet {
    private Repo repo;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        this.repo = (Repo) this.getServletContext().getAttribute("repo");
        this.mapper = (ObjectMapper) this.getServletContext().getAttribute("mapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String id = req.getParameter("id");
        if (id != null) {
            Transmission tm = repo.findTransmissionById(Long.parseLong(id));
            json = mapper.writeValueAsString(tm);
        } else {
            List<Transmission> list = repo.findAllTm();
            json = mapper.writeValueAsString(list);
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addTm".equals(action)) {
            Boolean isAutomatic = "AT".equals(req.getParameter("tmType"));
            String driveType = req.getParameter("driveType");
            Transmission tm = Transmission.of(isAutomatic, driveType);
            System.out.println(tm);
            boolean result = repo.create(tm);
            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}
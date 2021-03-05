package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.model.Engine;
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
import java.util.Set;

@WebServlet(urlPatterns = "/engine")
public class EngineServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MarkServlet.class);
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
            Set<Engine> list = repo.findAllEnginesOfModel(Long.parseLong(id));
            json = mapper.writeValueAsString(list);
        } else {
            List<Engine> list = repo.findAllEngines();
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
        if ("addEngine".equals(action)) {
            String engineName = req.getParameter("engineName").toLowerCase().trim();
            Integer enginePower = Integer.parseInt(req.getParameter("enginePower"));
            String fuel = req.getParameter("fuel").toLowerCase().trim();
            Float engineVolume = Float.parseFloat(req.getParameter("engineVolume"));
            boolean result = repo.create(Engine.of(engineName, enginePower, fuel, engineVolume));
            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}
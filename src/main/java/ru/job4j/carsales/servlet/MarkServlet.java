package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carsales.dto.ModelDto;
import ru.job4j.carsales.model.Mark;
import ru.job4j.carsales.model.Model;
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

@WebServlet(urlPatterns = "/mark")
public class MarkServlet extends HttpServlet {
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
            List<ModelDto> models = repo.findModelsByMarkId(Long.parseLong(id));
            json = mapper.writeValueAsString(models);
        } else {
            List<Mark> list = repo.findAllMarks();
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
        boolean result = false;
        if ("addMark".equals(action)) {
            String markName = req.getParameter("markName").toLowerCase().trim();
            result = repo.create(Mark.of(markName));
        } else if ("addModel".equals(action)) {
            String modelName = req.getParameter("modelName").toLowerCase().trim();
            Long markId = Long.parseLong(req.getParameter("markId"));
            Mark mark = repo.findMarkById(markId);
            result = repo.create(Model.of(modelName, mark));
        } else if ("addEngineToModel".equals(action)) {
            Long modelId = Long.parseLong(req.getParameter("modelId"));
            Long engineId = Long.parseLong(req.getParameter("engineId"));
            result = repo.applyEngineToModel(modelId, engineId);
        } else if ("delEngineFromModel".equals(action)) {
            Long modelId = Long.parseLong(req.getParameter("modelId"));
            Long engineId = Long.parseLong(req.getParameter("engineId"));
            result = repo.deleteEngineFromModel(modelId, engineId);
        }
        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
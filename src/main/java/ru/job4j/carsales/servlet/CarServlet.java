package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.dto.ModelDto;
import ru.job4j.carsales.model.*;
import ru.job4j.carsales.repo.Repo;
import ru.job4j.carsales.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/car")
public class CarServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServlet.class);
    private static final String AD_PIC_ = "AD_PIC_";

    private Repo repo;
    private ObjectMapper mapper;
    private String absPath = "/img";
    private String path = "img/";

    @Override
    public void init() throws ServletException {
        this.repo = (Repo) this.getServletContext().getAttribute("repo");
        this.mapper = (ObjectMapper) this.getServletContext().getAttribute("mapper");
        this.absPath = getServletContext().getRealPath(path);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long markId = Long.parseLong(req.getParameter("markId"));
        Long modelId = Long.parseLong(req.getParameter("modelId"));
        Boolean freshAd = Boolean.parseBoolean(req.getParameter("freshAd"));
        Boolean withPhotos = Boolean.parseBoolean(req.getParameter("withPhotos"));
        List<Announcement> list = repo.findAllAnnouncements(markId, modelId, freshAd, withPhotos);
        String json = mapper.writeValueAsString(list);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession hs = req.getSession();
        Account account = (Account) hs.getAttribute("user");
        String action = req.getParameter("action");
        boolean result = false;
        if ("addAd".equals(action) && account != null) {
            String vin = req.getParameter("vin").toLowerCase().trim();
            Integer created = Integer.parseInt(req.getParameter("created"));
            LocalDate localDate = LocalDate.of(created, 1, 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Long modelId = Long.parseLong(req.getParameter("modelId"));
            ModelDto model = repo.findModelDtoById(modelId);

            Long engineId = Long.parseLong(req.getParameter("engineId"));
            Engine engine = repo.findEngineById(engineId);

            Long transmissionId = Long.parseLong(req.getParameter("transmissionId"));
            Transmission transmission = repo.findTransmissionById(transmissionId);

            Car car = Car.of(vin, model, date);

            String color = req.getParameter("color");
            String description = req.getParameter("desc");
            Long price = Long.parseLong(req.getParameter("price"));
            Long run = Long.parseLong(req.getParameter("run"));
            String location = req.getParameter("location");

            Announcement announcement = Announcement.of(
                    car, engine, transmission, color, description, price, run, location, account);
            hs.setAttribute("announcement", announcement);
            result = true;
        } else if (action == null) {
            Announcement announcement = (Announcement) hs.getAttribute("announcement");
            if (announcement != null) {
                absPath = absPath + File.separator + announcement.getCar().getVin();
                path = path + announcement.getCar().getVin() + "/";
                List<String> photos = PhotoService.savePhotos(absPath, path, req, AD_PIC_, log);
                announcement.setPhotos(photos);
                result = true;
            }
        } else if ("saveAd".equals(action)) {
            Announcement announcement = (Announcement) hs.getAttribute("announcement");
            if (announcement != null) {
                result = repo.create(announcement);
            }
        }
        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

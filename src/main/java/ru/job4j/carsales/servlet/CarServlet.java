package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.dto.AnnouncementDto;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.model.Announcement;
import ru.job4j.carsales.repo.Repo;
import ru.job4j.carsales.repo.filter.AdFilter;
import ru.job4j.carsales.service.AuthService;
import ru.job4j.carsales.service.JsonService;
import ru.job4j.carsales.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/car")
@MultipartConfig()
public class CarServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServlet.class);

    private Repo repo;
    private ObjectMapper mapper;
    private String imgDir;

    @Override
    public void init() throws ServletException {
        this.repo = (Repo) this.getServletContext().getAttribute("repo");
        this.mapper = (ObjectMapper) this.getServletContext().getAttribute("mapper");
        this.imgDir = String.valueOf(this.getServletContext().getAttribute("imgDir"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String json;
        if (id != null) {
            AnnouncementDto announcement = repo.findAnnouncementById(Long.parseLong(id));
            req.setAttribute("ad", announcement);
            req.getRequestDispatcher("/watchAd.jsp").forward(req, resp);
        } else {
            AdFilter filter = new AdFilter();
            Long markId = Long.parseLong(req.getParameter("markId"));
            Long modelId = Long.parseLong(req.getParameter("modelId"));
            Boolean freshAd = Boolean.parseBoolean(req.getParameter("freshAd"));
            Boolean withPhotos = Boolean.parseBoolean(req.getParameter("withPhotos"));
            filter.setMarkId(markId);
            filter.setModelId(modelId);
            filter.setFreshAd(freshAd);
            filter.setWithPhotos(withPhotos);
            List<AnnouncementDto> list = repo.findAllAnnouncements(filter);
            json = mapper.writeValueAsString(list);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("json");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(json);
            writer.flush();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Account account = AuthService.getLoggedInAccount(req);

        String id = req.getParameter("id");
        if (id != null) {
            Long adId = Long.valueOf(id);
            if (repo.tryCloseAd(account.getId(), adId))
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            AtomicInteger response = new AtomicInteger(HttpServletResponse.SC_OK);
            Announcement announcement = new Announcement();
            announcement.setOwner(account);

            Part part = req.getPart("blob");
            if (part != null && "application/json".equals(part.getContentType())) {
                try {
                    String json = JsonService.readContent(part);
                    JsonService.loadFields(mapper.readTree(json), announcement, repo);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    response.set(HttpServletResponse.SC_BAD_REQUEST);
                }
            }

            if (response.get() == HttpServletResponse.SC_OK) {
                String path = "img/" + announcement.getCar().getVin() + "/";
                int i = 0;
                for (Part image : req.getParts()) {
                    if (image != null
                            && image.getContentType() != null
                            && image.getContentType().contains("image")) {
                        try {
                            String photo = PhotoService.getPhoto(image,
                                    imgDir + File.separator + announcement.getCar().getVin(),
                                    "AD_PIC_" + i++);
                            if (!photo.isEmpty()) {
                                announcement.getPhotos().add(path + photo);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            response.set(HttpServletResponse.SC_BAD_REQUEST);
                            break;
                        }
                    }
                }
            }
            if ((response.get() == HttpServletResponse.SC_OK))
                repo.create(announcement);
            resp.setStatus(response.get());
        }
    }
}
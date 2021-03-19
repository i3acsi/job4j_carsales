package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.repo.Repo;
import ru.job4j.carsales.service.AuthService;
import ru.job4j.carsales.service.JsonService;
import ru.job4j.carsales.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@WebServlet(urlPatterns = "/userInfo")
@MultipartConfig()
public class AccountServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);
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
        Account account = AuthService.getLoggedInAccount(req);
        if (account != null) {
            String id = req.getParameter("id");
            resp.setContentType("json");
            String json;
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
            if (id != null) {
                Account user = repo.findAccountById(Long.parseLong(id));
                json = mapper.writeValueAsString(List.of(user.getTelephone(), user.getEmail()));
            } else {
                json = mapper.writeValueAsString(List.of(
                        repo.findAnnouncementsOfAccount(account.getId()), account));
            }
            writer.write(json);
            writer.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Account account = AuthService.getLoggedInAccount(req);
        AtomicInteger response = new AtomicInteger(HttpServletResponse.SC_OK);
        Account tmp = Account.copy(account);
        req.getParts().forEach(part -> {
            if (part != null
                    && part.getContentType() != null
                    && (response.get() == HttpServletResponse.SC_OK)) {
                if (part.getContentType().contains("image")) {
                    try {
                        String path = "img/" + account.getId() + "/";
                        String photo = PhotoService.getPhoto(part,
                                imgDir + File.separator + account.getId(),
                                "USER_PIC_");
                        if (!photo.isEmpty()) {
                            account.setUserPic(path + photo);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        response.set(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else if ("application/json".equals(part.getContentType())) {
                    try {
                        String json = JsonService.readContent(part);
                        JsonService.loadFields(mapper.readTree(json), account);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        response.set(HttpServletResponse.SC_BAD_REQUEST);
                    }
                }
            }
        });
        if ((response.get() == HttpServletResponse.SC_OK))
            repo.update(account);
        else {
            req.getSession().setAttribute("user", tmp);
        }
        resp.setStatus(response.get());
    }
}
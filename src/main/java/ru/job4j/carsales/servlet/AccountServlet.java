package ru.job4j.carsales.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.repo.Repo;
import ru.job4j.carsales.service.AuthService;
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
import java.util.List;

@WebServlet(urlPatterns = "/userInfo")
public class AccountServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);
    private static final String USER_PIC_ = "USER_PIC_";
    private Repo repo;
    private ObjectMapper mapper;
    private String absPath = "/img";
    private String path = "img/";

    @Override
    public void init() throws ServletException {
        this.repo = (Repo) this.getServletContext().getAttribute("repo");
        this.mapper = (ObjectMapper) this.getServletContext().getAttribute("mapper");
        this.absPath = getServletContext().getRealPath(absPath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession hs = req.getSession();
        Account account = (Account) hs.getAttribute("user");
        if (account != null) {
            String json = mapper.writeValueAsString(account);
            resp.setContentType("json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(json);
            writer.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession hs = req.getSession();
        Account account = (Account) hs.getAttribute("user");
        int response = HttpServletResponse.SC_UNAUTHORIZED;
        if ("loadFields".equals(action)) {
            String password = req.getParameter("password");
            if (account != null && AuthService.checkPwd(password, account.getPassword())) {
                String id = req.getParameter("id");
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");
                String location = req.getParameter("location");

                account.setName(name);
                account.setEmail(email);
                phone = phone.replaceAll("[^0-9,]", "");
                account.setTelephone(Long.parseLong(phone));
                account.setLocation(location);
                response = HttpServletResponse.SC_OK;
            }
        } else if (action == null) {
            absPath = absPath + File.separator + account.getId();
            path = path + account.getId() + "/";
            List<String> photo = PhotoService.savePhotos(absPath, path, req, USER_PIC_ + account.getId(), log);
            if (photo.size() > 0) {
                account.setUserPic(photo.get(0));
                response = HttpServletResponse.SC_OK;
            }
        } else if ("saveAcc".equals(action)) {
            repo.update(account);
            response = HttpServletResponse.SC_OK;
        }
        resp.setStatus(response);
    }
}
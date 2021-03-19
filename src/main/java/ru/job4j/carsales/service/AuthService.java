package ru.job4j.carsales.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.model.Role;
import ru.job4j.carsales.repo.Repo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthService {
    private static final String SALT = "DEFAULT_SALT";

    private static String encodePassword(String password) {
        return DigestUtils.sha256Hex(password + SALT);
    }

    public static boolean checkPwd(String input, String stored) {
        return stored.equals(encodePassword(input));
    }

    public static boolean checkAndSetCredentials(String email, String password, HttpServletRequest req, Repo repo) {
        Account account = repo.findAccountByEmail(email);
        boolean result = false;
        if (account != null) {
            String passwordFromDB = account.getPassword();
            String passwordHash = encodePassword(password);
            if (passwordFromDB.equals(passwordHash)) {
                req.getSession().setAttribute("user", account);
                if (account.getRole().getName().equals("ADMIN"))
                    req.getSession().setAttribute("role", "ADMIN");
                result = true;
            }
        }
        return result;
    }

    public static boolean createAccount(String name, String email, String password, String phone, Repo repo) {
        String encodedPwd = encodePassword(password);
        phone = phone.replaceAll("[^0-9,]", "");
        Long telephone = Long.parseLong(phone);
        Account account = Account.of(name, email, encodedPwd, telephone);
        if ("root@local.ru".equals(email))
            account.setRole(Role.of("ADMIN"));
        return repo.create(account);
    }

    public static boolean isAdmin(Account account) {
        return (account != null && account.getRole().getName().equals("ADMIN"));
    }

    public static Account getLoggedInAccount(HttpServletRequest request) {
        HttpSession hs = request.getSession();
        return (Account) hs.getAttribute("user");

    }
}
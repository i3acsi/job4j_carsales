package ru.job4j.carsales.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.repo.Repo;

import javax.servlet.http.HttpServletRequest;

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
                result = true;
            }
        }
        return result;
    }

    public static boolean createAccount(String name, String email, String password, String phone, Repo repo) {
        String encodedPwd = encodePassword(password);
        phone = phone.replaceAll("[^0-9,]", "");
        Long telephone = Long.parseLong(phone);
        return repo.create(Account.of(name, email, encodedPwd, telephone));
    }

    public static boolean isAdmin(Account account) {
        return (account.getRole().getName().equals("ADMIN"));
    }
}
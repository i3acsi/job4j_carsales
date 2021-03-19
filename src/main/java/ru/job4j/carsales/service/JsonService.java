package ru.job4j.carsales.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.IOUtils;
import ru.job4j.carsales.dto.ModelDto;
import ru.job4j.carsales.model.*;
import ru.job4j.carsales.repo.Repo;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JsonService {
    public static String readContent(Part part) throws IOException {
        StringWriter writer = new StringWriter();
        String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(part.getInputStream(), writer, encoding);
        return writer.toString();
    }

    public static void loadFields(JsonNode jsonNode, Account account) throws Exception {
        String password = jsonNode.get("password").asText();
        if (!AuthService.checkPwd(password, account.getPassword()))
            throw new Exception("wrong password");
        String name = jsonNode.get("name").asText();
        String email = jsonNode.get("email").asText();
        String phone = jsonNode.get("phone").asText();
        String location = jsonNode.get("location").asText();

        phone = phone.replaceAll("[^0-9,]", "");

        account.setName(name);
        account.setEmail(email);
        account.setTelephone(Long.parseLong(phone));
        account.setLocation(location);
    }

    public static void loadFields(JsonNode jsonNode, Announcement announcement, Repo repo) throws Exception {
        String vin = jsonNode.get("vin").asText().toLowerCase().trim();
        Integer created = jsonNode.get("created").asInt();
        LocalDate localDate = LocalDate.of(created, 1, 1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Long modelId = jsonNode.get("modelId").asLong();
        ModelDto model = repo.findModelDtoById(modelId);

        Car car = Car.of(vin, model, date);
        announcement.setCar(car);

        Long engineId = jsonNode.get("engineId").asLong();
        Engine engine = repo.findEngineById(engineId);
        announcement.setEngine(engine);

        Long transmissionId = jsonNode.get("transmissionId").asLong();
        Transmission transmission = repo.findTransmissionById(transmissionId);
        announcement.setTransmission(transmission);

        String color = jsonNode.get("color").asText();
        announcement.setColor(color);

        String description = jsonNode.get("desc").asText();
        announcement.setDescription(description);

        Long price = jsonNode.get("price").asLong();
        announcement.setPrice(price);

        Long run = jsonNode.get("run").asLong();
        announcement.setRun(run);

        String location = jsonNode.get("location").asText();
        announcement.setLocation(location);
    }
}

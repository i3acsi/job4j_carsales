package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.model.Announcement;
import ru.job4j.carsales.model.Car;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
class AnnouncementRepo {
    private static final Logger log = LoggerFactory.getLogger(AnnouncementRepo.class);
    private final Store store;

    @SuppressWarnings("unchecked")
    List<Announcement> findAllAnnouncements(Filter filter) {
        return store.tx(session -> {
            Query query = session.createQuery(filter.doFilter());
            filter.getParametersSet().forEach(query::setParameter);
            log.info(query.getQueryString());
            return query.list();
        });
    }

    boolean create(Announcement announcement) {
        boolean result = false;
        if ((announcement.getEngine() != null) && (announcement.getTransmission() != null)) {
            try {
                Car car = store.findOrCreate(Car.class, announcement.getCar(), "car_vin", announcement.getCar().getVin());
                if (car.equals(announcement.getCar())) {
                    announcement.setCar(car);
                    result = store.persist(announcement).getId() > 0;
                } else log.error("В базе уже есть машина с таким vin, но модель или год создания не совпадают");
            } catch (Exception e) {
                log.error("Ошибка встаки объявления", e);
            }
        }
        return result;
    }
}
package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.model.Announcement;
import ru.job4j.carsales.model.Car;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@RequiredArgsConstructor(staticName = "of")
class AnnouncementRepo {
    private static final Logger log = LoggerFactory.getLogger(AnnouncementRepo.class);
    private final Store store;

    private Date yesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        return Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @SuppressWarnings("unchecked")
    List<Announcement> findAllAnnouncements(Long markId, Long modelId, boolean freshAd, boolean withPhotos) {
        return store.tx(session -> {
            String sql = "select distinct a from Announcement a join fetch a.photos ";
            StringJoiner joiner = new StringJoiner(" and ");
            if (modelId != null && modelId > 0L) {
                joiner.add("a.car.model.id = :moId");
            } else if (markId != null && markId > 0L) {
                joiner.add("a.car.model.mark.id = :mId");
            }
            if (freshAd)
                joiner.add("a.created > : date");
            if (withPhotos) {
                joiner.add("size(a.photos) > 0");
            } else {
                joiner.add("size(a.photos) = 0");
            }
            String filter = joiner.toString();
            if (!filter.isEmpty()) {
                sql += " where " + filter;
            }
            Query query = session.createQuery(sql);
            if (modelId != null && modelId > 0L) {
                query.setParameter("moId", modelId);
            } else if (markId != null && markId > 0L) {
                query.setParameter("mId", markId);
            }
            if (freshAd) {
                Date date = yesterday();
                query.setParameter("date", date);
            }
            log.info(query.toString());
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
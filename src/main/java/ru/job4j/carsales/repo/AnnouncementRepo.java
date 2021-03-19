package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.carsales.dto.AnnouncementDto;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.model.Announcement;
import ru.job4j.carsales.model.Car;
import ru.job4j.carsales.repo.filter.AdFilter;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
class AnnouncementRepo {
    private static final Logger log = LoggerFactory.getLogger(AnnouncementRepo.class);
    private final Store store;

    @SuppressWarnings("unchecked")
    List<AnnouncementDto> findAllAnnouncements(AdFilter filter) {
        return store.tx(session -> {
            Query query = session.createQuery(filter.buildFilterQuery());
            filter.getParametersMap().forEach(query::setParameter);
            log.info(query.getQueryString());
            return query.list();
        });
    }

    AnnouncementDto findAnnouncementById(Long id) {
        return store.tx(session ->
                (AnnouncementDto) session.createQuery(" select distinct a from AnnouncementDto a left join fetch a.photos where a.id = :fId")
                        .setParameter("fId", id)
                        .uniqueResult()
        );
    }

    List<AnnouncementDto> findAnnouncementsOfAccount(Long accountId) {
        return store.tx(session ->
                session.createQuery(" select distinct a from AnnouncementDto a left join fetch a.photos where a.owner = :fId", AnnouncementDto.class)
                        .setParameter("fId", accountId)
                        .list()
        );
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

    public boolean tryCloseAd(Long accountId, Long announcementId) {
        return store.tx(session -> {
            Announcement a = session.get(Announcement.class, announcementId);
            Account ac = a.getOwner();
            System.out.println(ac);
            if (!ac.getId().equals(accountId)) {
                return false;
            } else {
                a.setActive(false);
                a.setUpdated(new Date(System.currentTimeMillis()));
                session.merge(a);
                return true;
            }
        });
    }
}
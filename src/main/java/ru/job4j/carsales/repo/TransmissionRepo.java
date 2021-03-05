package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import ru.job4j.carsales.model.Transmission;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
class TransmissionRepo {
    private final Store store;

    List<Transmission> findAllTm() {
        return store.findAll(Transmission.class);
    }

    boolean create(Transmission tm) {
        return store.persist(tm).getId() > 0L;
    }

    Transmission findTransmissionById(Long id) {
        return store.findById(id, Transmission.class);
    }
}
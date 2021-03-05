package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import ru.job4j.carsales.model.Mark;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
class MarkRepo {
    private final Store store;

    List<Mark> findAllMarks() {
        return store.findAll(Mark.class);
    }

    Mark findMarkById(Long id) {
        return store.findById(id, Mark.class);
    }

    boolean create(Mark mark) {
        return store.persist(mark).getId() > 0L;
    }
}
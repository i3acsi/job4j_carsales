package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import ru.job4j.carsales.model.Engine;
import ru.job4j.carsales.model.Model;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(staticName = "of")
class EngineRepo {
    private final Store store;

    Engine findEngineById(Long id) {
        return store.findById(id, Engine.class);
    }

    List<Engine> findAllEngines() {
        return store.findAll(Engine.class);
    }

    Set<Engine> findAllEnginesOfModel(Long modelId) {
        return store.tx(session -> {
            Query q = session.createQuery(
                    "select m from Model m join fetch m.engines where m.id = :id");
            q.setParameter("id", modelId);
            return ((Model) q.uniqueResult()).getEngines();
        });
    }

    boolean applyEngineToModel(Long modelId, Long engineId) {
        return store.tx(session -> {
            Model model = session.get(Model.class, modelId);
            Engine engine = session.get(Engine.class, engineId);
            boolean result = model.getEngines().add(engine);
            session.update(model);
            return result;
        });
    }

    boolean deleteEngineFromModel(Long modelId, Long engineId) {
        return store.tx(session -> {
            Model model = session.get(Model.class, modelId);
            Engine engine = session.get(Engine.class, engineId);
            boolean result = model.getEngines().remove(engine);
            session.update(model);
            return result;
        });
    }

    boolean create(Engine engine) {
        return store.persist(engine).getId() > 0L;
    }
}
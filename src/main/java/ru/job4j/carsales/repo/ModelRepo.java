package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import ru.job4j.carsales.dto.ModelDto;
import ru.job4j.carsales.model.Model;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
class ModelRepo {
    private final Store store;

    boolean create(Model model) {
        return store.persist(model).getId() > 0L;
    }

    ModelDto findModelDtoById(Long id) {
        return store.findById(id, ModelDto.class);
    }

    Model findModelById(Long id) {
        return store.findById(id, Model.class);
    }


    @SuppressWarnings("unchecked")
    List<ModelDto> findModelsByMarkId(Long id) {
        return store.tx(session -> {
            Query query = session.createQuery("select m from ModelDto m where m.mark.id = :mId");
            query.setParameter("mId", id);
            return query.list();
        });
//        return store.tx(session -> {
//            Query q = session.createQuery(
//                    "select m from Mark m join fetch m.models where m.id = :id");
//            q.setParameter("id", id);
//            return  ((Mark) q.uniqueResult()).getModels();
//        });
    }

}

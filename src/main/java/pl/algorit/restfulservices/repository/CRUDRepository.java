package pl.algorit.restfulservices.repository;

import java.util.Collection;
import java.util.Optional;

public interface CRUDRepository<OBJECT extends CRUDEntity> {

    OBJECT create(OBJECT movie);

    Optional<OBJECT> getById(int id);

    void update(OBJECT movie);

    void deleteById(int id);

    Collection<OBJECT> getAll();
}

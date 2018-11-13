package pl.algorit.restfulservices.repository;

import java.util.Collection;

public interface CRUDRepository<OBJECT extends CRUDEntity> {

    OBJECT create(OBJECT movie);

    OBJECT getById(int id);

    void update(OBJECT movie);

    void deleteById(int id);

    Collection<OBJECT> getAll();
}

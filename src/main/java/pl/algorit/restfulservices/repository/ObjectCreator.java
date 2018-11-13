package pl.algorit.restfulservices.repository;

import java.util.Collection;

public interface ObjectCreator<OBJECT extends CRUDEntity> {

    Collection<OBJECT> create();
}

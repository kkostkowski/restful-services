package pl.algorit.restfulservices.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static pl.algorit.restfulservices.utils.AdditionalCollectors.toSingle;

public class CRUDInMemoryRepository<ENTITY extends CRUDEntity> implements CRUDRepository<ENTITY> {
    private AtomicInteger latestId;
    private List<ENTITY> objects = new ArrayList<>();

    public CRUDInMemoryRepository(Set<ObjectCreator<ENTITY>> objectCreators) {
        objects.addAll(objectCreators
                .stream()
                .peek(objectCreator -> {
                    checkArgument(objectCreator.create().stream().noneMatch(object -> object.getId() == null),
                            "Creator must creates objects with id");
                })
                .map(ObjectCreator::create)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        val objectsById = objects.stream().collect(Collectors.groupingBy(ENTITY::getId));
        Preconditions.checkArgument(objectsById.entrySet().stream().noneMatch(idsCount -> idsCount.getValue().size() > 1), "Creators conflict - creators created object with the same id");


        latestId = new AtomicInteger(objects.stream()
                .map(ENTITY::getId)
                .max(Integer::compareTo)
                .orElse(0));
    }

    @Override
    public ENTITY create(@NonNull ENTITY object) {
        checkArgument(object.getId() == null, "can't create object with id already assigned");
        val created = (ENTITY) object.withId(latestId.incrementAndGet());
        objects.add(created);
        return created;
    }

    @Override
    public ENTITY getById(@NonNull int id) {
        checkArgument(objects.stream().anyMatch(m -> m.getId().equals(id)), "object with such id does not exists");
        return objects.stream()
                .filter(object -> object.getId() == id)
                .collect(toSingle());
    }

    @Override
    public void update(@NonNull ENTITY object) {
        checkArgument(object.getId() != null, "can't update object without id");
        checkArgument(objects.stream().anyMatch(m -> m.getId().equals(object.getId())), "object with such id does not exists");
        val oldMovie = objects.stream().filter(m -> m.getId().equals(object.getId())).collect(toSingle());
        Collections.replaceAll(objects, oldMovie, object);
    }

    @Override
    public void deleteById(@NonNull int id) {
        checkArgument(objects.stream().anyMatch(m -> m.getId().equals(id)), "object with such id does not exists");
        objects.removeIf(object -> object.getId().equals(id));
    }

    @Override
    public Collection<ENTITY> getAll() {
        return ImmutableSet.copyOf(objects);
    }

    @VisibleForTesting
    Collection<ENTITY> getObjects() {
        return objects;
    }

    @VisibleForTesting
    AtomicInteger getLatestId() {
        return latestId;
    }
}

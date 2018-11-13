package pl.algorit.restfulservices.services.movies.details;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepository;
import pl.algorit.restfulservices.repository.ObjectCreator;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MovieRepositoryInMemory extends CRUDInMemoryRepository<Movie> implements MovieRepository {

    public MovieRepositoryInMemory(Set<ObjectCreator<Movie>> objectCreators) {
        super(objectCreators);
    }

}

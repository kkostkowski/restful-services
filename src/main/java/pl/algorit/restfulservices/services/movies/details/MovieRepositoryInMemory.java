package pl.algorit.restfulservices.services.movies.details;

import org.springframework.stereotype.Repository;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepository;

import java.util.Set;

@Repository
public class MovieRepositoryInMemory extends CRUDInMemoryRepository<Movie> implements MovieRepository {

    @SuppressWarnings("unchecked")
    public MovieRepositoryInMemory(Set<MovieCreator> objectCreators) {
        super((Set) objectCreators);
    }

}

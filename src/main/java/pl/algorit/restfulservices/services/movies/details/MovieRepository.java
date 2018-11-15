package pl.algorit.restfulservices.services.movies.details;

import java.util.Collection;
import java.util.Optional;

public interface MovieRepository {

    Movie create(Movie movie);

    Optional<Movie> getById(int id);

    void update(Movie movie);

    void deleteById(int id);

    Collection<Movie> getAll();
}

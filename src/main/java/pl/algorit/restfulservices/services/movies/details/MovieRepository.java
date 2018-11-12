package pl.algorit.restfulservices.services.movies.details;

import java.util.Collection;

public interface MovieRepository {

    Movie create(Movie movie);

    Movie getById(int id);

    void update(Movie movie);

    void deleteById(int id);

    Collection<Movie> getAll();
}

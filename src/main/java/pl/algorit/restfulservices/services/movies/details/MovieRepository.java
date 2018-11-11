package pl.algorit.restfulservices.services.movies.details;

public interface MovieRepository {

    void create(Movie movie);

    Movie getById(int id);

    void update(Movie movie);

    void deleteById(int id);
}

package pl.algorit.restfulservices.services.movies.details;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

import static pl.algorit.restfulservices.utils.AdditionalCollectors.toSingle;

@Repository
public class MovieRepositoryInMemory implements MovieRepository {
    private Set<Movie> movies = new HashSet<>();

    @Override
    public void create(Movie movie) {
        Preconditions.checkArgument(!movies.contains(movie), "such movie already exists");
        movies.add(movie);
    }

    @Override
    public Movie getById(int id) {
        return movies.stream()
                .filter(movie -> movie.getId() == id)
                .collect(toSingle());
    }

    @Override
    public void update(Movie movie) {
        Preconditions.checkArgument(movies.contains(movie), "such movie does not exists");
        movies.add(movie);
    }

    @Override
    public void deleteById(int id) {
        movies.stream()
                .filter(movie -> movie.getId() == id)
                .iterator()
                .remove();
    }
}

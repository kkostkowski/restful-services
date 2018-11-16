package pl.algorit.restfulservices.services.movies.details;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class MovieDetailsServiceImpl implements MovieDetailsService {

    private MovieRepository movieRepository;

    @Override
    @Cacheable(value = "movies")
    public Movie getMovieDetails(int movieId) {
        return movieRepository.getById(movieId)
                .orElseThrow(() -> new MovieNotFound(movieId));
    }

    @Override
    @Cacheable(value = "movies")
    public Collection<Movie> getAllMovieDetails() {
        return movieRepository.getAll();
    }

    @Override
    @CachePut(value = "movies")
    public Movie createMovie(@NonNull Movie movie) {
        return movieRepository.create(movie);
    }
}

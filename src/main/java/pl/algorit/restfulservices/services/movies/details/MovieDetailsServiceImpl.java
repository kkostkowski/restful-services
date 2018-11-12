package pl.algorit.restfulservices.services.movies.details;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class MovieDetailsServiceImpl implements MovieDetailsService {

    private MovieRepository movieRepository;

    @Override
    public Movie getMovieDetails(int movieId) {
        return movieRepository.getById(movieId);
    }

    @Override
    public Collection<Movie> getAllMovieDetails() {
        return movieRepository.getAll();
    }

    @Override
    public Movie createMovie(@NonNull Movie movie) {
        return movieRepository.create(movie);
    }
}

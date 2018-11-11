package pl.algorit.restfulservices.services.movies.details;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieDetailsServiceImpl implements MovieDetailsService {

    private MovieRepository movieRepository;

    @Override
    public Movie getMovieDetails(int movieId) {
        return movieRepository.getById(movieId);
    }
}

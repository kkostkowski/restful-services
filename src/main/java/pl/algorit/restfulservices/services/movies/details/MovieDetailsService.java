package pl.algorit.restfulservices.services.movies.details;

import java.util.Collection;

public interface MovieDetailsService {
    Movie getMovieDetails(int movieId);

    Collection<Movie> getAllMovieDetails();

    Movie createMovie(Movie movie);

}

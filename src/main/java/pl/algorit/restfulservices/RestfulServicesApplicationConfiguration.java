package pl.algorit.restfulservices;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.algorit.restfulservices.services.movies.details.Movie;
import pl.algorit.restfulservices.services.movies.details.MovieCreator;

import java.util.Collection;

@Configuration
public class RestfulServicesApplicationConfiguration {

    private Collection<Movie> testMovies = ImmutableSet.<Movie>builder()
            .add(Movie.builder().id(1).title("First").description("FirstDescription").build())
            .add(Movie.builder().id(2).title("Second").description("SecondDescription").build())
            .add(Movie.builder().id(3).title("Third").description("ThirdDescription").build())
            .build();

    @Bean
    public MovieCreator testMoviesCreator() {
        return () -> testMovies;
    }
}

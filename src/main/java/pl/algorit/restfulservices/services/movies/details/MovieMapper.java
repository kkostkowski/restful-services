package pl.algorit.restfulservices.services.movies.details;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie map(@NonNull MovieDetailsDTO movieDetailsDTO) {
        return Movie.builder()
                .id(movieDetailsDTO.getId())
                .title(movieDetailsDTO.getTitle())
                .description(movieDetailsDTO.getDescription())
                .build();
    }

    public MovieDetailsDTO map(@NonNull Movie movie) {
        return MovieDetailsDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .build();
    }
}

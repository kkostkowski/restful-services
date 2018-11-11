package pl.algorit.restfulservices.services.movies.details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "id")
@Builder
public class Movie {
    @NonNull
    int id;

    @NonNull
    String title;
    String description;
}

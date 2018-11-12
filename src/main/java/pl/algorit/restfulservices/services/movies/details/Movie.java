package pl.algorit.restfulservices.services.movies.details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@EqualsAndHashCode(of = {"title", "description"})
@Wither
@Builder
public class Movie {
    Integer id;
    @NonNull
    String title;
    String description;
}

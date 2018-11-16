package pl.algorit.restfulservices.services.movies.details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;
import pl.algorit.restfulservices.repository.CRUDEntity;

import java.io.Serializable;

@Value
@EqualsAndHashCode(of = {"title", "description"})
@Builder
@Wither
public class Movie implements CRUDEntity, Serializable {
    Integer id;
    @NonNull
    String title;
    String description;
}

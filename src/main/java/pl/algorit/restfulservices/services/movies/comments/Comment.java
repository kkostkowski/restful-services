package pl.algorit.restfulservices.services.movies.comments;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;
import pl.algorit.restfulservices.repository.CRUDEntity;

import java.io.Serializable;

@Value
@EqualsAndHashCode(of = {"movieId", "username", "message"})
@Builder
@Wither
public class Comment implements CRUDEntity, Serializable {
    Integer id; //id is required by this CRUD entity framework

    Integer movieId;

    @NonNull
    String username;
    @NonNull
    String message;
}

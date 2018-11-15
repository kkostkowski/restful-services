package pl.algorit.restfulservices.services.movies.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"movieId", "username", "message"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    Integer id;

    Integer movieId;

    String username;

    String message;
}

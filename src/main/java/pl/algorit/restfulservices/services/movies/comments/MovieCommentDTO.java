package pl.algorit.restfulservices.services.movies.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCommentDTO {
    Integer id;
    Integer movieId;
    String username;
    String message;

}

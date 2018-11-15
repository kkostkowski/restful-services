package pl.algorit.restfulservices.services.movies.detailsandcomments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.algorit.restfulservices.services.movies.comments.CommentDTO;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsDTO;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailsAndCommentsDTO {
    private MovieDetailsDTO movieDetails;
    private Collection<CommentDTO> movieComments;
}

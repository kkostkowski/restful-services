package pl.algorit.restfulservices.services.movies.detailsandcomments;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;
import pl.algorit.restfulservices.services.movies.comments.CommentDTO;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsDTO;

import java.util.Random;

@Component
public class DetailsAndCommentsQuery implements GraphQLQueryResolver {

    public Integer simpleQuery() {
        return new Random().nextInt();
    }

    public DetailsAndCommentsDTO getMovieDetailsAndComments(int movieId) {
        return DetailsAndCommentsDTO.builder()
                .movieDetails(MovieDetailsDTO.builder()
                        .id(3)
                        .description("descri")
                        .title("titi")
                        .build())
                .movieComments(ImmutableList.of(
                        CommentDTO.builder()
                                .id(44)
                                .movieId(3)
                                .message("guma")
                                .username("balonowy")
                                .build(),
                        CommentDTO.builder()
                                .id(45)
                                .movieId(3)
                                .message("guma2")
                                .username("balonowy2")
                                .build())
                )
                .build();
    }

    public Iterable<DetailsAndCommentsDTO> findAllMovieDetailsWithComments() {
        return ImmutableList.of(
                DetailsAndCommentsDTO.builder()
                        .build(),
                DetailsAndCommentsDTO.builder()
                        .build()
        );
    }

}

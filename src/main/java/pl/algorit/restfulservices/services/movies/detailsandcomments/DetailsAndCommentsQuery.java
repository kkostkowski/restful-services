package pl.algorit.restfulservices.services.movies.detailsandcomments;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import pl.algorit.restfulservices.services.movies.comments.CommentDTO;
import pl.algorit.restfulservices.services.movies.comments.CommentMapper;
import pl.algorit.restfulservices.services.movies.comments.CommentsService;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsDTO;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsService;
import pl.algorit.restfulservices.services.movies.details.MovieMapper;

@AllArgsConstructor
public class DetailsAndCommentsQuery implements GraphQLQueryResolver {

    private MovieDetailsService movieDetailsService;
    private MovieMapper movieMapper;

    private CommentsService commentsService;
    private CommentMapper commentMapper;

    public DetailsAndCommentsDTO getMovieDetailsAndComments(int movieId) {
        val movie = movieDetailsService.getMovieDetails(movieId);
        val comments = commentsService.getComments(movieId);

        return DetailsAndCommentsDTO.builder()
                .movieDetails(movieMapper.map(movie))
                .movieComments(commentMapper.map(comments))
                .build();
    }
}

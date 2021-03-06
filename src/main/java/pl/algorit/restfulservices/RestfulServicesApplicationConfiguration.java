package pl.algorit.restfulservices;

import com.google.common.collect.ImmutableSet;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.algorit.restfulservices.services.movies.comments.Comment;
import pl.algorit.restfulservices.services.movies.comments.CommentCreator;
import pl.algorit.restfulservices.services.movies.comments.CommentMapper;
import pl.algorit.restfulservices.services.movies.comments.CommentsService;
import pl.algorit.restfulservices.services.movies.details.Movie;
import pl.algorit.restfulservices.services.movies.details.MovieCreator;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsService;
import pl.algorit.restfulservices.services.movies.details.MovieMapper;
import pl.algorit.restfulservices.services.movies.detailsandcomments.DetailsAndCommentsQuery;

import java.util.Collection;

@Configuration
public class RestfulServicesApplicationConfiguration {

    private Collection<Movie> testMovies = ImmutableSet.<Movie>builder()
            .add(Movie.builder().id(1).title("First").description("FirstDescription").build())
            .add(Movie.builder().id(2).title("Second").description("SecondDescription").build())
            .add(Movie.builder().id(3).title("Third").description("ThirdDescription").build())
            .build();

    private Collection<Comment> comments = ImmutableSet.<Comment>builder()
            .add(Comment.builder().id(1).movieId(1).username("usr1").message("hello moto1").build())
            .add(Comment.builder().id(2).movieId(1).username("usr2").message("hello moto2").build())
            .add(Comment.builder().id(3).movieId(1).username("usr3").message("hello moto3").build())
            .build();

    @Bean
    public MovieCreator firstAndOnlyMovieCreator() {
        return () -> testMovies;
    }

    @Bean
    public CommentCreator firstAndOnlyCommentCreator() {
        return () -> comments;
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new pl.algorit.restfulservices.graphqlutils.GraphQLErrorHandler();
    }

    @Bean
    public DetailsAndCommentsQuery detailsAndCommentsQuery(MovieDetailsService movieDetailsService,
                                                           MovieMapper movieMapper,
                                                           CommentsService commentsService,
                                                           CommentMapper commentMapper) {
        return new DetailsAndCommentsQuery(movieDetailsService,
                movieMapper,
                commentsService,
                commentMapper);
    }
}

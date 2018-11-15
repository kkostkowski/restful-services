package pl.algorit.restfulservices.services.movies.comments;

import java.util.Collection;
import java.util.Optional;

public interface CommentRepository {

    Comment create(Comment movie);

    Optional<Comment> getById(int id);

    void update(Comment movie);

    void deleteById(int id);

    Collection<Comment> getAll();

    Collection<Comment> getByMovieId(int movieId);
}

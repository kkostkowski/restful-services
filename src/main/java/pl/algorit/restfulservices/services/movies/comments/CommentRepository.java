package pl.algorit.restfulservices.services.movies.comments;

import java.util.Collection;

public interface CommentRepository {

    Comment create(Comment movie);

    Comment getById(int id);

    void update(Comment movie);

    void deleteById(int id);

    Collection<Comment> getAll();
}

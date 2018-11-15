package pl.algorit.restfulservices.services.movies.comments;

import java.util.Collection;

public interface CommentsService {
    Comment getComment(int id);

    Collection<Comment> getAllComments();

    Comment createComment(Comment movie);

}

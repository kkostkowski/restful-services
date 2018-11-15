package pl.algorit.restfulservices.services.movies.comments;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private CommentRepository commentRepository;

    @Override
    public Comment getComment(int id) {
        return commentRepository.getById(id)
                .orElseThrow(() -> new CommentNotFound(id));
    }

    @Override
    public Collection<Comment> getAllComments() {
        return commentRepository.getAll();
    }

    @Override
    public Comment createComment(@NonNull Comment movie) {
        return commentRepository.create(movie);
    }

    @Override
    public Collection<Comment> getComments(int movieId) {
        return commentRepository.getByMovieId(movieId);
    }
}

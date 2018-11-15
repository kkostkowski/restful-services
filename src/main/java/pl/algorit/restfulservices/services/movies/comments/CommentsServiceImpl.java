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
        return commentRepository.getById(id);
    }

    @Override
    public Collection<Comment> getAllComments() {
        return commentRepository.getAll();
    }

    @Override
    public Comment createComment(@NonNull Comment movie) {
        return commentRepository.create(movie);
    }
}

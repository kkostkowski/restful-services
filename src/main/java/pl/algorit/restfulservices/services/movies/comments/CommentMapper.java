package pl.algorit.restfulservices.services.movies.comments;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public Comment map(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .movieId(commentDTO.getMovieId())
                .username(commentDTO.getUsername())
                .message(commentDTO.getMessage())
                .build();
    }

    public CommentDTO map(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .movieId(comment.getMovieId())
                .username(comment.getUsername())
                .message(comment.getMessage())
                .build();
    }

    public Collection<CommentDTO> map(Collection<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDTO.builder()
                        .movieId(comment.getMovieId())
                        .id(comment.getId())
                        .username(comment.getUsername())
                        .message(comment.getMessage())
                        .build())
                .collect(Collectors.toList());
    }
}

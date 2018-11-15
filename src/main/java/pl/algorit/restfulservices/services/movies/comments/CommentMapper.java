package pl.algorit.restfulservices.services.movies.comments;

import org.springframework.stereotype.Component;

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
}

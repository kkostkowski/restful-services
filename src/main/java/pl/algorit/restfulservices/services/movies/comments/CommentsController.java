package pl.algorit.restfulservices.services.movies.comments;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/api/v1/comments")
public class CommentsController {

    private CommentsService commentsService;

    private CommentMapper commentMapper;

    @GetMapping("/")
    public Collection<CommentDTO> getAllComments() {
        val all = commentsService.getAllComments();

        return all.stream()
                .map(commentMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentDTO getComment(@PathVariable Integer id) {
        val comment = commentsService.getComment(id);

        return commentMapper.map(comment);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO) {
        val comment = commentMapper.map(commentDTO);
        val created = commentsService.createComment(comment);
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}

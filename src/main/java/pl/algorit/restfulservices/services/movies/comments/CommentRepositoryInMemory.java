package pl.algorit.restfulservices.services.movies.comments;

import org.springframework.stereotype.Repository;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryInMemory extends CRUDInMemoryRepository<Comment> implements CommentRepository {
    @SuppressWarnings("unchecked")
    public CommentRepositoryInMemory(Set<CommentCreator> objectCreators) {
        super((Set) objectCreators);
    }

    @Override
    public Collection<Comment> getByMovieId(int movieId) {
        return objects.stream()
                .filter(comment -> movieId == comment.getMovieId())
                .collect(Collectors.toList());
    }
}

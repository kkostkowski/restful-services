package pl.algorit.restfulservices.services.movies.comments;

import org.springframework.stereotype.Repository;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepository;

import java.util.Set;

@Repository
public class CommentRepositoryInMemory extends CRUDInMemoryRepository<Comment> implements CommentRepository {
    @SuppressWarnings("unchecked")
    public CommentRepositoryInMemory(Set<CommentCreator> objectCreators) {
        super((Set) objectCreators);
    }

}

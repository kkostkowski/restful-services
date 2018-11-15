package pl.algorit.restfulservices.services.movies.comments;

import com.google.common.collect.ImmutableSet;
import lombok.val;
import org.testng.annotations.Test;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepositoryTest;

import java.util.Collections;
import java.util.Set;

@Test(groups = "unit")
public class CommentRepositoryInMemoryTest extends CRUDInMemoryRepositoryTest<Comment, CommentRepositoryInMemory, CommentCreator> {

    public CommentRepositoryInMemoryTest() {
        super(new CommentRepositoryInMemory(Collections.emptySet()));
    }

    @Override
    protected CommentRepositoryInMemory emptyRepository() {
        return new CommentRepositoryInMemory(Collections.emptySet());
    }

    @Override
    protected CommentRepositoryInMemory repositoryWithObjects(Set<Comment> comments) {
        val creators = ImmutableSet.<CommentCreator>of(getCreator(comments));
        return new CommentRepositoryInMemory(creators);
    }

    @Override
    protected CommentRepositoryInMemory repositoryWithCreators(Set<CommentCreator> commentCreators) {
        return new CommentRepositoryInMemory(commentCreators);
    }

    @Override
    protected CommentCreator getCreator(Set<Comment> comments) {
        return () -> ImmutableSet.copyOf(comments);
    }

    @Override
    protected Comment getTestObjectWithoutId() {
        return Comment.builder().movieId(1).username("no_id_title").message("no_id_message").build();
    }

    @Override
    protected Comment getTestObjectOneWithId() {
        return Comment.builder().id(1).movieId(1).username("one").message("oneMessage").build();
    }

    @Override
    protected Comment getTestObjectTwoWithId() {
        return Comment.builder().id(2).movieId(1).username("two").message("twoMessage").build();
    }

    @Override
    protected Comment getTestObjectTwoPrimWithId() {
        return Comment.builder().id(2).movieId(1).username("twoPrim").message("twoPrimMessage").build();
    }

    @Override
    protected Comment getTestObjectThreeWithId() {
        return Comment.builder().id(3).movieId(1).username("three").message("threeMessage").build();
    }

}
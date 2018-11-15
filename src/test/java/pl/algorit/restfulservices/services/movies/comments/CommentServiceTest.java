package pl.algorit.restfulservices.services.movies.comments;

import com.google.common.collect.ImmutableSet;
import lombok.Cleanup;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class CommentServiceTest {
    private CommentsService objectUnderTest;

    private CommentRepository commentRepositoryFake;

    @BeforeMethod
    public void setup() {
        commentRepositoryFake = Mockito.mock(CommentRepository.class);
        objectUnderTest = new CommentsServiceImpl(commentRepositoryFake);
    }

    public void getComment_existingId2_returnComment2Details() {
        val id = 2;
        val existing = Comment.builder()
                .id(2)
                .movieId(1)
                .username("lolo")
                .message("hello")
                .build();

        when(commentRepositoryFake.getById(2)).thenReturn(existing);

        val result = objectUnderTest.getComment(id);

        val expected = Comment.builder()
                .id(2)
                .movieId(1)
                .username("lolo")
                .message("hello")
                .build();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    public void getAllComments_oneCommentExists_returnsOneComment() {
        val existing = Comment.builder()
                .id(2)
                .movieId(1)
                .username("lolo")
                .message("hello")
                .build();

        when(commentRepositoryFake.getAll()).thenReturn(ImmutableSet.of(existing));

        val result = objectUnderTest.getAllComments();

        Assertions.assertThat(result)
                .containsExactlyInAnyOrder(existing)
                .doesNotHaveDuplicates();
    }

    public void create_newComment_returnsNewCommentWithId() {
        val comment = Comment.builder()
                .movieId(1)
                .username("lolo")
                .message("hello")
                .build();

        val created = Comment.builder()
                .id(2)
                .movieId(1)
                .username("lolo")
                .message("hello")
                .build();

        when(commentRepositoryFake.create(comment)).thenReturn(created);

        val result = objectUnderTest.createComment(comment);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(result).isEqualTo(created);
        softly.assertThat(result.getId()).isEqualTo(2);
    }
}

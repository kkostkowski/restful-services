package pl.algorit.restfulservices.services.movies.comments;

import com.google.common.collect.ImmutableSet;
import lombok.Cleanup;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test(groups = "integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CommentsControllerTestIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommentsController objectUnderTest;

    public void getAllComments_usingDefaultConfiguration_returnsAllInitializedComments() {
        val expected = ImmutableSet.<CommentDTO>builder()
                .add(CommentDTO.builder().id(1).movieId(1).username("usr1").message("hello moto1").build())
                .add(CommentDTO.builder().id(2).movieId(1).username("usr2").message("hello moto2").build())
                .add(CommentDTO.builder().id(3).movieId(1).username("usr3").message("hello moto3").build())
                .build();

        val all = objectUnderTest.getAllComments();

        Assertions.assertThat(all)
                .containsExactlyInAnyOrderElementsOf(expected)
                .doesNotHaveDuplicates();
    }

    public void getComment_id2_returnsSecondComment() {
        val id = 2;
        val expected = CommentDTO.builder().id(2).movieId(1).username("usr2").message("hello moto2").build();

        val comment = objectUnderTest.getComment(id);

        Assertions.assertThat(comment).isEqualTo(expected);
    }

    @DirtiesContext
    public void createComment_newComment_createsNewComment() {
        val comment = CommentDTO.builder().movieId(1).username("usr2").message("hello moto2").build();

        val movieCreationResponse = objectUnderTest.createComment(comment);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(movieCreationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(movieCreationResponse.getHeaders().get("Location")).containsExactly("http://localhost/4");
    }

    public void createMovie_existingMovie_throwsException() {
        val comment = CommentDTO.builder().id(2).movieId(1).username("usr2").message("hello moto2").build();

        Assertions.assertThatThrownBy(() -> objectUnderTest.createComment(comment))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't create object with id already assigned");
    }

}
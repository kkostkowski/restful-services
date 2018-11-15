package pl.algorit.restfulservices.services.movies.comments;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Test(groups = "unit")
public class CommentMapperTest {

    private CommentMapper objectUnderTest = new CommentMapper();

    public void map_CommentToCommentDTO_mapsAllFields() {
        val given = Comment.builder()
                .id(2)
                .movieId(1)
                .username("usr2")
                .message("hello moto2")
                .build();

        val expected = CommentDTO.builder()
                .id(2)
                .movieId(1)
                .username("usr2")
                .message("hello moto2")
                .build();

        val result = objectUnderTest.map(given);


        Assertions.assertThat(result).isEqualToComparingFieldByField(expected);
    }

    public void map_CommentDTOtoComment_mapsAllFields() {
        val given = CommentDTO.builder()
                .id(2)
                .movieId(1)
                .username("usr2")
                .message("hello moto2")
                .build();

        val expected = Comment.builder()
                .id(2)
                .movieId(1)
                .username("usr2")
                .message("hello moto2")
                .build();


        val result = objectUnderTest.map(given);

        Assertions.assertThat(result).isEqualToComparingFieldByField(expected);
    }
}
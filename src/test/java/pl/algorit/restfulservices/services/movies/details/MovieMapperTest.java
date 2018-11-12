package pl.algorit.restfulservices.services.movies.details;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Test(groups = "unit")
public class MovieMapperTest {

    private MovieMapper objectUnderTest = new MovieMapper();

    public void map_MovieToMovieDetailsDTO_mapsAllFields() {
        val given = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        val expected = MovieDetailsDTO.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        val result = objectUnderTest.map(given);


        Assertions.assertThat(result).isEqualToComparingFieldByField(expected);
    }

    public void map_MovieDetailsDTOtoMovie_mapsAllFields() {
        val given = MovieDetailsDTO.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        val expected = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();


        val result = objectUnderTest.map(given);

        Assertions.assertThat(result).isEqualToComparingFieldByField(expected);
    }
}
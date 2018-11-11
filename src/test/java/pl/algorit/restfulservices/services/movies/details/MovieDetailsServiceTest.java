package pl.algorit.restfulservices.services.movies.details;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class MovieDetailsServiceTest {

    private MovieDetailsService objectUnderTest;
    private MovieRepository movieRepositoryFake;

    @BeforeMethod
    public void setup() {
        movieRepositoryFake = Mockito.mock(MovieRepository.class);
        objectUnderTest = new MovieDetailsServiceImpl(movieRepositoryFake);
    }

    public void getMovieDetails_existingId2_returnsMovie2Details() {
        val movieId = 2;
        val existingMovie = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        when(movieRepositoryFake.getById(2)).thenReturn(existingMovie);

        val result = objectUnderTest.getMovieDetails(movieId);

        val expectedMovie = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        Assertions.assertThat(result).isEqualTo(expectedMovie);
    }
}

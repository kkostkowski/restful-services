package pl.algorit.restfulservices.services.movies.details;

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

    public void getAllMovieDetails_oneMovieExists_returnsOneMovie() {
        val existingMovie = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        when(movieRepositoryFake.getAll()).thenReturn(ImmutableSet.of(existingMovie));

        val result = objectUnderTest.getAllMovieDetails();

        Assertions.assertThat(result)
                .containsExactlyInAnyOrder(existingMovie)
                .doesNotHaveDuplicates();
    }

    public void create_newMovie_returnsNewMovieWithId() {
        val movie = Movie.builder()
                .title("second title")
                .description("tests")
                .build();

        val createdMovie = Movie.builder()
                .id(2)
                .title("second title")
                .description("tests")
                .build();

        when(movieRepositoryFake.create(movie)).thenReturn(createdMovie);

        val result = objectUnderTest.createMovie(movie);

        @Cleanup
        val sa = new AutoCloseableSoftAssertions();
        sa.assertThat(result).isEqualTo(createdMovie);
        sa.assertThat(result.getId()).isEqualTo(2);
    }
}

package pl.algorit.restfulservices.services.movies.details;

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
public class MovieDetailsControllerTestIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private MovieDetailsController objectUnderTest;

    public void getAllMovieDetails_usingDefaultConfiguration_returnsAllInitializedMovies() {
        val expectedMovies = ImmutableSet.<MovieDetailsDTO>builder()
                .add(MovieDetailsDTO.builder().id(1).title("First").description("FirstDescription").build())
                .add(MovieDetailsDTO.builder().id(2).title("Second").description("SecondDescription").build())
                .add(MovieDetailsDTO.builder().id(3).title("Third").description("ThirdDescription").build())
                .build();

        val allMovies = objectUnderTest.getAllMovieDetails();

        Assertions.assertThat(allMovies)
                .containsExactlyInAnyOrderElementsOf(expectedMovies)
                .doesNotHaveDuplicates();
    }

    public void getMovieDetails_id2_returnsSecondMovie() {
        val movieId = 2;
        val expectedMovie = MovieDetailsDTO.builder().id(2).title("Second").description("SecondDescription").build();

        val movie = objectUnderTest.getMovieDetails(movieId);

        Assertions.assertThat(movie).isEqualTo(expectedMovie);
    }

    @DirtiesContext
    public void createMovie_newMovie_createsNewMovie() {
        val newMovie = MovieDetailsDTO.builder().title("new").description("newDescription").build();

        val movieCreationResponse = objectUnderTest.createMovie(newMovie);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(movieCreationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(movieCreationResponse.getHeaders().get("Location")).containsExactly("http://localhost/4");
    }

    public void createMovie_existingMovie_throwsException() {
        val newMovie = MovieDetailsDTO.builder().id(1).title("new").description("newDescription").build();

        Assertions.assertThatThrownBy(() -> objectUnderTest.createMovie(newMovie))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't create movie with id already assigned");
    }

}
package pl.algorit.restfulservices.services.movies.details;

import com.google.common.collect.ImmutableSet;
import lombok.Cleanup;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

@Test(groups = "unit")
public class MovieRepositoryInMemoryTest {
    private MovieRepositoryInMemory objectUnderTest;

    public void __constructor_usesMovieCreator_toInitializeInMemoryDataAndIncreaseMoviesCounter() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getMovies())
                .containsExactlyInAnyOrder(oneMovie)
                .doesNotHaveDuplicates();
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(1);
    }

    public void __constructor_usingMovieCreator_doesNotAllowToCreateObjectsWithoutId() {
        val oneMovie = Movie.builder().title("one").description("oneDescription").build();
        val movieCreators = ImmutableSet.<MovieCreator>of(() -> Collections.singleton(oneMovie));
        Assertions.assertThatThrownBy(() -> objectUnderTest = new MovieRepositoryInMemory(movieCreators))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Creator must creates objects with id");
    }

    public void __constructor_usingMovieCreator_doesNotAllowToCreateObjectsWithTheSameIds() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val oneMoviePrim = Movie.builder().id(1).title("onePrim").description("oneDescriptionPrim").build();
        val movieCreators = ImmutableSet.<MovieCreator>of(
                () -> Collections.singleton(oneMovie),
                () -> Collections.singleton(oneMoviePrim));

        Assertions.assertThatThrownBy(() -> objectUnderTest = new MovieRepositoryInMemory(movieCreators))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Creators conflict - creators created object with the same id");
    }

    public void create_newMovie_createsNewMovieWithId() {
        objectUnderTest = emptyRepository();
        val newMovie = Movie.builder().title("new").description("newDescription").build();

        val result = objectUnderTest.create(newMovie);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(result).isEqualTo(newMovie);
        softly.assertThat(result.getId()).isEqualTo(1);
    }

    public void getAll_emptyRepository_returnsEmptyCollection() {
        objectUnderTest = emptyRepository();

        val result = objectUnderTest.getAll();

        Assertions.assertThat(result).isEmpty();
    }

    public void getAll_oneObjectInRepository_returnsSingleElementCollection() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie);

        val result = objectUnderTest.getAll();

        Assertions.assertThat(result)
                .containsExactlyInAnyOrder(oneMovie)
                .doesNotHaveDuplicates();
    }

    public void getById_getId1fromTwoMovies_returnsMovieWithId1AndDoesNotChangeMovies() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        val result = objectUnderTest.getById(1);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(result).isEqualTo(oneMovie);
        softly.assertThat(objectUnderTest.getMovies()).hasSize(2);
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void getById_movieNotExistingWithinMovies_throwsException() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        Assertions.assertThatThrownBy(() -> objectUnderTest.getById(3))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("movie with such id does not exists");
    }

    public void update_movieWithId2_updatesMoviesCollection() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        val twoMoviePrim = Movie.builder().id(2).title("twoPrim").description("twoDescriptionPrim").build();
        objectUnderTest.update(twoMoviePrim);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getMovies())
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(oneMovie, twoMoviePrim))
                .doesNotHaveDuplicates();
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void update_movieNotYetExistingWithinMovies_throwsException() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        val threePrim = Movie.builder().id(3).title("threePrim").description("threeDescriptionPrim").build();

        Assertions.assertThatThrownBy(() -> objectUnderTest.update(threePrim))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("movie with such id does not exists");
    }

    public void update_movieWithoutId_throwsException() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        val twoMoviePrim = Movie.builder().title("aaatwoPrim").description("aaaaaatwoDescriptionPrim").build();

        Assertions.assertThatThrownBy(() -> objectUnderTest.update(twoMoviePrim))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't update movie without id");
    }

    public void delete_id1_removesMovieWithId1AndUpdatesMovies() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        objectUnderTest.deleteById(1);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getMovies()).containsExactly(twoMovie);
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void delete_movieNotExistingWithinMovies_throwsException() {
        val oneMovie = Movie.builder().id(1).title("one").description("oneDescription").build();
        val twoMovie = Movie.builder().id(2).title("two").description("twoDescription").build();
        objectUnderTest = repositoryWithMovies(oneMovie, twoMovie);

        Assertions.assertThatThrownBy(() -> objectUnderTest.deleteById(3))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("movie with such id does not exists");
    }


    private MovieRepositoryInMemory emptyRepository() {
        return new MovieRepositoryInMemory(Collections.emptySet());
    }

    private MovieRepositoryInMemory repositoryWithMovies(Movie... movies) {
        val args = ImmutableSet.<MovieCreator>of(() -> ImmutableSet.copyOf(movies));
        return new MovieRepositoryInMemory(args);
    }
}
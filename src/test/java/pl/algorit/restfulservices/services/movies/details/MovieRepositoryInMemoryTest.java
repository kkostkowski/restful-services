package pl.algorit.restfulservices.services.movies.details;

import com.google.common.collect.ImmutableSet;
import lombok.val;
import org.testng.annotations.Test;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepositoryTest;

import java.util.Collections;
import java.util.Set;

@Test(groups = "unit")
public class MovieRepositoryInMemoryTest extends CRUDInMemoryRepositoryTest<Movie, MovieRepositoryInMemory, MovieCreator> {

    public MovieRepositoryInMemoryTest() {
        super(new MovieRepositoryInMemory(Collections.emptySet()));
    }

    @Override
    protected MovieRepositoryInMemory emptyRepository() {
        return new MovieRepositoryInMemory(Collections.emptySet());
    }

    @Override
    protected MovieRepositoryInMemory repositoryWithObjects(Set<Movie> movies) {
        val creators = ImmutableSet.<MovieCreator>of(getCreator(movies));
        return new MovieRepositoryInMemory(creators);
    }

    @Override
    protected MovieRepositoryInMemory repositoryWithCreators(Set<MovieCreator> movieCreators) {
        return new MovieRepositoryInMemory(movieCreators);
    }

    @Override
    protected MovieCreator getCreator(Set<Movie> movies) {
        return () -> ImmutableSet.copyOf(movies);
    }

    @Override
    protected Movie getTestObjectWithoutId() {
        return Movie.builder().title("no_id_title").description("no_id_description").build();
    }

    @Override
    protected Movie getTestObjectOneWithId() {
        return Movie.builder().id(1).title("one").description("oneDescription").build();
    }

    @Override
    protected Movie getTestObjectTwoWithId() {
        return Movie.builder().id(2).title("two").description("twoDescription").build();
    }

    @Override
    protected Movie getTestObjectTwoPrimWithId() {
        return Movie.builder().id(2).title("twoPrim").description("twoPrimDescription").build();
    }

    @Override
    protected Movie getTestObjectThreeWithId() {
        return Movie.builder().id(3).title("three").description("threeDescription").build();
    }

}
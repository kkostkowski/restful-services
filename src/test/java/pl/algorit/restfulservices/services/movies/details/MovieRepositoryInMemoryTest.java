package pl.algorit.restfulservices.services.movies.details;

import com.google.common.collect.ImmutableSet;
import lombok.val;
import org.testng.annotations.Test;
import pl.algorit.restfulservices.repository.CRUDInMemoryRepositoryTest;
import pl.algorit.restfulservices.repository.ObjectCreator;

import java.util.Collections;
import java.util.Set;

@Test(groups = "unit")
public class MovieRepositoryInMemoryTest extends CRUDInMemoryRepositoryTest<Movie, MovieRepositoryInMemory> {

    public MovieRepositoryInMemoryTest() {
        super(new MovieRepositoryInMemory(Collections.emptySet()));
    }

    @Override
    protected MovieRepositoryInMemory emptyRepository() {
        return new MovieRepositoryInMemory(Collections.emptySet());
    }

    @Override
    protected MovieRepositoryInMemory repositoryWithObjects(Set<Movie> movies) {
        val args = ImmutableSet.<ObjectCreator<Movie>>of(() -> ImmutableSet.copyOf(movies));
        return new MovieRepositoryInMemory(args);
    }

    @Override
    protected MovieRepositoryInMemory repositoryWithCreators(Set<ObjectCreator<Movie>> movieCreators) {
        return new MovieRepositoryInMemory(movieCreators);
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
        return Movie.builder().id(2).title("twoPrim").description("twoDescriptionPrim").build();
    }

    @Override
    protected Movie getTestObjectThreeWithId() {
        return Movie.builder().id(3).title("three").description("threeDescription").build();
    }

}
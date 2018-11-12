package pl.algorit.restfulservices.services.movies.details;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static pl.algorit.restfulservices.utils.AdditionalCollectors.toSingle;

@Repository
public class MovieRepositoryInMemory implements MovieRepository {
    private AtomicInteger latestId;
    private List<Movie> movies = new ArrayList<>();

    public MovieRepositoryInMemory(Set<MovieCreator> movieCreators) {
        movies.addAll(movieCreators
                .stream()
                .peek(movieCreator -> {
                    checkArgument(movieCreator.create().stream().noneMatch(movie -> movie.getId() == null),
                            "Creator must creates objects with id");
                })
                .map(MovieCreator::create)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        val moviesById = movies.stream().collect(Collectors.groupingBy(Movie::getId));
        Preconditions.checkArgument(moviesById.entrySet().stream().noneMatch(idsCount -> idsCount.getValue().size() > 1), "Creators conflict - creators created object with the same id");


        latestId = new AtomicInteger(movies.stream()
                .map(Movie::getId)
                .max(Integer::compareTo)
                .orElse(0));
    }

    @Override
    public Movie create(@NonNull Movie movie) {
        checkArgument(movie.getId() == null, "can't create movie with id already assigned");
        val created = movie.withId(latestId.incrementAndGet());
        movies.add(created);
        return created;
    }

    @Override
    public Movie getById(int id) {
        checkArgument(movies.stream().anyMatch(m -> m.getId().equals(id)), "movie with such id does not exists");
        return movies.stream()
                .filter(movie -> movie.getId() == id)
                .collect(toSingle());
    }

    @Override
    public void update(@NonNull Movie movie) {
        checkArgument(movie.getId() != null, "can't update movie without id");
        checkArgument(movies.stream().anyMatch(m -> m.getId().equals(movie.getId())), "movie with such id does not exists");
        val oldMovie = movies.stream().filter(m -> m.getId().equals(movie.getId())).collect(toSingle());
        Collections.replaceAll(movies, oldMovie, movie);
    }

    @Override
    public void deleteById(int id) {
        checkArgument(movies.stream().anyMatch(m -> m.getId().equals(id)), "movie with such id does not exists");
        movies.removeIf(movie -> movie.getId() == id);
    }

    @Override
    public Collection<Movie> getAll() {
        return ImmutableSet.copyOf(movies);
    }

    @VisibleForTesting
    Collection<Movie> getMovies() {
        return movies;
    }

    @VisibleForTesting
    AtomicInteger getLatestId() {
        return latestId;
    }
}

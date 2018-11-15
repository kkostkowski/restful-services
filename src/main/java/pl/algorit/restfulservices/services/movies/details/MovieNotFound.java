package pl.algorit.restfulservices.services.movies.details;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieNotFound extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public MovieNotFound(Integer movieId) {
        super(String.format("Movie with id=%d not found.", movieId));
        extensions.put("movieId", movieId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}

package pl.algorit.restfulservices.services.movies.comments;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentNotFound extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public CommentNotFound(Integer commentId) {
        super(String.format("Comment with id=%d not found.", commentId));
        extensions.put("commentId", commentId);
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
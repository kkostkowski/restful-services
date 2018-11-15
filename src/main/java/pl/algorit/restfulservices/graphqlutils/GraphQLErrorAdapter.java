package pl.algorit.restfulservices.graphqlutils;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GraphQLErrorAdapter implements GraphQLError {

    private GraphQLError graphqlError;

    @Override
    public Map<String, Object> getExtensions() {
        return graphqlError.getExtensions();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return graphqlError.getLocations();
    }

    @Override
    public ErrorType getErrorType() {
        return graphqlError.getErrorType();
    }

    @Override
    public List<Object> getPath() {
        return graphqlError.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return graphqlError.toSpecification();
    }

    @Override
    public String getMessage() {
        return (graphqlError instanceof ExceptionWhileDataFetching)
                ? ((ExceptionWhileDataFetching) graphqlError).getException().getMessage()
                : graphqlError.getMessage();
    }
}

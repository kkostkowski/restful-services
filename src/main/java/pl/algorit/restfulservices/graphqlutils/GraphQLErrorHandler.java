package pl.algorit.restfulservices.graphqlutils;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Streams.concat;

public class GraphQLErrorHandler implements graphql.servlet.GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        val clientErrors = errors.stream()
                .filter(this::isClientError);

        val serverErrors = errors.stream()
                .filter(e -> !isClientError(e))
                .map(GraphQLErrorAdapter::new);

        return concat(clientErrors, serverErrors)
                .collect(Collectors.toList());
    }

    private boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }

}

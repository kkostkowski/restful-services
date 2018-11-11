package pl.algorit.restfulservices.utils;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AdditionalCollectors {

    public static <T> Collector<T, ?, T> toSingle() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException(String.format("Can't get single element because collection has %d elements", list.size()));
                    }
                    return list.get(0);
                }
        );
    }
}

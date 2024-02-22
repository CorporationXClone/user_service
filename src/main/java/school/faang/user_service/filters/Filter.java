package school.faang.user_service.filters;

import java.util.stream.Stream;

public interface Filter<U, T> {
    boolean isApplicable(T filters);
    Stream<U> apply(Stream<U> stream, T filters);
}

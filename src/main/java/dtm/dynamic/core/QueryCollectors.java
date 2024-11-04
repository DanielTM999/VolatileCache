package dtm.dynamic.core;

import java.util.List;
import java.util.Optional;

public interface QueryCollectors<T> {
    List<T> toList();
    T single();
    Optional<T> singleOptional();
    int count();
    boolean isEmpty();
    List<T> distinct();

}

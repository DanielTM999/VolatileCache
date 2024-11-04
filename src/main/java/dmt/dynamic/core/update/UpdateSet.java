package dmt.dynamic.core.update;

import java.util.function.Consumer;

public interface UpdateSet<T> {
    void update(Consumer<T> action);
}

package dmt.dynamic.core.delete;

import java.util.function.Predicate;

public interface DeleteWhereStruct<T> {
    void where(Predicate<T> predicate);
    void where();
}

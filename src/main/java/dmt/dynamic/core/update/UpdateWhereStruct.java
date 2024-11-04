package dmt.dynamic.core.update;

import java.util.function.Predicate;

public interface UpdateWhereStruct<T> {
    UpdateSet<T> where(Predicate<T> predicate);
    UpdateSet<T> where();
}

package dtm.dynamic.core.select;

import java.util.function.Predicate;
import dtm.dynamic.core.QueryCollectors;

public interface SelectWhereStruct<T> {
    QueryCollectors<T> where(Predicate<T> predicate);
    QueryCollectors<T> where();
}

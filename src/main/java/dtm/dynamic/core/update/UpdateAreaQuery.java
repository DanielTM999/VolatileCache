package  dtm.dynamic.core.update;

public interface UpdateAreaQuery<T> {
    UpdateWhereStruct<T> from(String area);
    UpdateWhereStruct<T> from();
}

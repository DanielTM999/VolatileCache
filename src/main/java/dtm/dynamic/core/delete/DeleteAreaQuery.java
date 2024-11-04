package dtm.dynamic.core.delete;

public interface DeleteAreaQuery<T>{
    DeleteWhereStruct<T> from(String area);
    DeleteWhereStruct<T> from();
}
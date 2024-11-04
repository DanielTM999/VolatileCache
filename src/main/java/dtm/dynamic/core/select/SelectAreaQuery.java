package dtm.dynamic.core.select;

public interface SelectAreaQuery<T> {
    SelectWhereStruct<T> from(String area);
    SelectWhereStruct<T> from();
}

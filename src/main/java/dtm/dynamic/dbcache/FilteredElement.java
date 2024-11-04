package dtm.dynamic.dbcache;

public class FilteredElement<T> {
    private final T element;
    private final int originalIndex;

    public FilteredElement(T element, int originalIndex) {
        this.element = element;
        this.originalIndex = originalIndex;
    }

    public T getElement() {
        return element;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }
}
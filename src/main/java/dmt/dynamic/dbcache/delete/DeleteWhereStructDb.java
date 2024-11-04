package dmt.dynamic.dbcache.delete;

import java.util.List;
import java.util.function.Predicate;

import dmt.dynamic.core.delete.DeleteWhereStruct;
import dmt.dynamic.dbcache.FilteredElement;

public class DeleteWhereStructDb<T> implements DeleteWhereStruct<T> {

    private List<FilteredElement<T>> filteredElements;
    private List<Object> originalList;
    

    public DeleteWhereStructDb(List<FilteredElement<T>> filteredElements, List<Object> originalList) {
        this.filteredElements = filteredElements;
        this.originalList = originalList;
    }

    @Override
    public void where(Predicate<T> predicate) {
        final List<FilteredElement<T>> matchedElements = filteredElements.parallelStream()
            .filter(fe -> predicate.test(fe.getElement())) 
        .toList();

        for (FilteredElement<T> iterable_element : matchedElements) {
            originalList.remove(iterable_element.getOriginalIndex());
        }
    }

    @Override
    public void where() {
        Predicate<T> alwaysTrue = t -> true;
        where(alwaysTrue);
    }
    
}

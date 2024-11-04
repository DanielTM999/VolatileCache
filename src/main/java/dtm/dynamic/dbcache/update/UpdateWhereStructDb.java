package dtm.dynamic.dbcache.update;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import dtm.dynamic.core.update.UpdateSet;
import dtm.dynamic.core.update.UpdateWhereStruct;
import dtm.dynamic.dbcache.FilteredElement;


public class UpdateWhereStructDb<T> implements UpdateWhereStruct<T>{

    private List<FilteredElement<T>> filteredElements;
    private List<Object> originalList;
    

    public UpdateWhereStructDb(List<FilteredElement<T>> filteredElements, List<Object> originalList) {
        this.filteredElements = filteredElements;
        this.originalList = originalList;
    }

    @Override
    public UpdateSet<T> where(Predicate<T> predicate) {
        final List<FilteredElement<T>> matchedElements = filteredElements.parallelStream()
            .filter(fe -> predicate.test(fe.getElement())) 
        .toList();

        return new UpdateSet<>() {

            @Override
            public void update(Consumer<T> action) {
                for (FilteredElement<T> filteredElement : matchedElements) {
                    T toUpdate = filteredElement.getElement();
                    action.accept(toUpdate);
                    originalList.set(filteredElement.getOriginalIndex(), toUpdate);
                }
            }
            
        };
    }

    @Override
    public UpdateSet<T> where() {
        Predicate<T> alwaysTrue = t -> true;
        return where(alwaysTrue);
    }


    
}

package dmt.dynamic.dbcache.select;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import dmt.dynamic.core.QueryCollectors;
import dmt.dynamic.core.select.SelectWhereStruct;


public class SelectWhereStructDb<T> implements SelectWhereStruct<T>{

    private List<T> filterList;

    public SelectWhereStructDb(List<T> filterList){
        this.filterList = filterList;
    }

    @Override
    public QueryCollectors<T> where(Predicate<T> predicate) {
        final List<T> filteredList = filterList.parallelStream().filter(predicate).toList();

        return new QueryCollectors<>() {
            
            @Override
            public List<T> toList() {
                return (filteredList == null)? new ArrayList<>(): filteredList;
            }

            @Override
            public T single() {
                List<T> t = (filteredList == null)? new ArrayList<>(): filteredList;
                T value = null;

                if(!t.isEmpty()){
                    value = t.get(0);
                }

                return value;
            }

            @Override
            public Optional<T> singleOptional() {
                return Optional.ofNullable(single());
            }

            @Override
            public int count() {
                if(filteredList != null){
                    return filteredList.size();
                }
                return 0;
            }

            @Override
            public boolean isEmpty() {
                if(filteredList != null){
                    return filteredList.isEmpty();
                }
                return true;
            }

            @Override
            public List<T> distinct() {
                return filterList.parallelStream()
                    .distinct()
                .toList();
            }
            
        };
    }

    @Override
    public QueryCollectors<T> where() {
        return where((p) -> true);
    }
    
}

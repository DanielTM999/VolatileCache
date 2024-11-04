package dmt.dynamic.dbcache.update;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dmt.dynamic.core.update.UpdateAreaQuery;
import dmt.dynamic.core.update.UpdateWhereStruct;
import dmt.dynamic.dbcache.FilteredElement;

public class UpdateAreaQueryDb<T> implements UpdateAreaQuery<T>{

    private Map<String, List<Object>> database;
    private Class<T> target;

    public UpdateAreaQueryDb(Map<String, List<Object>> database, Class<T> target){
        this.database = database;
        this.target = target;
    }

    @Override
    public UpdateWhereStruct<T> from(String area) {
        List<FilteredElement<T>> filteredElements = new ArrayList<>();
        
        List<Object> elements = database.getOrDefault(area, new ArrayList<>());
        for (int i = 0; i < elements.size(); i++) {
            Object val = elements.get(i);
            T filtered = filter(target, val);
            if (filtered != null) {
                filteredElements.add(new FilteredElement<>(filtered, i));
            }
        }

        return new UpdateWhereStructDb<T>(filteredElements, elements);
    }

    @Override
    public UpdateWhereStruct<T> from() {
        return from("default");
    }

    private T filter(Class<T> target, Object obj){
        if (target.isAssignableFrom(obj.getClass())){
            return target.cast(obj);
        }
        return null; 
    }
    
}

package dtm.dynamic.dbcache.delete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dtm.dynamic.core.delete.DeleteAreaQuery;
import dtm.dynamic.core.delete.DeleteWhereStruct;
import dtm.dynamic.dbcache.FilteredElement;

public class DeleteAreaQueryDb<T> implements DeleteAreaQuery<T>{

    private Map<String, List<Object>> database;
    private Class<T> target;

    public DeleteAreaQueryDb(Map<String, List<Object>> database, Class<T> target){
        this.database = database;
        this.target = target;
    }

    @Override
    public DeleteWhereStruct<T> from(String area) {
        List<FilteredElement<T>> filteredElements = new ArrayList<>();
        
        List<Object> elements = database.getOrDefault(area, new ArrayList<>());
        for (int i = 0; i < elements.size(); i++) {
            Object val = elements.get(i);
            T filtered = filter(target, val);
            if (filtered != null) {
                filteredElements.add(new FilteredElement<>(filtered, i));
            }
        }

        return new DeleteWhereStructDb<>(filteredElements, elements);
    }

    @Override
    public DeleteWhereStruct<T> from() {
        return from("default");
    }

    private T filter(Class<T> target, Object obj){
        if (target.isAssignableFrom(obj.getClass())){
            return target.cast(obj);
        }
        return null; 
    }
    
}

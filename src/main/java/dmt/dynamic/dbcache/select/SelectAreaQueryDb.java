package dmt.dynamic.dbcache.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import dmt.dynamic.core.select.SelectAreaQuery;
import dmt.dynamic.core.select.SelectWhereStruct;

public class SelectAreaQueryDb<T> implements SelectAreaQuery<T>{

    private Map<String, List<Object>> database;
    private Class<T> target;

    public SelectAreaQueryDb(Map<String, List<Object>> database, Class<T> target){
        this.database = database;
        this.target = target;
    }

    @Override
    public SelectWhereStruct<T> from(String area) {
        List<T> filterElements = database.getOrDefault(area, new ArrayList<>())
            .parallelStream()
            .map(val -> filter(target, val))
            .filter(Objects::nonNull)
        .toList();
        return new SelectWhereStructDb<T>(filterElements);
    }

    @Override
    public SelectWhereStruct<T> from() {
        return from("default");
    }

    private T filter(Class<T> target, Object obj){
        if (target.isAssignableFrom(obj.getClass())){
            return target.cast(obj);
        }
        return null; 
    }
    
}

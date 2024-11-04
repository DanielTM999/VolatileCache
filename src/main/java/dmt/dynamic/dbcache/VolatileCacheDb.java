package dmt.dynamic.dbcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import dmt.dynamic.core.VolatileCache;
import dmt.dynamic.core.delete.DeleteAreaQuery;
import dmt.dynamic.core.insert.InsertAreaQuery;
import dmt.dynamic.core.select.SelectAreaQuery;
import dmt.dynamic.core.update.UpdateAreaQuery;
import dmt.dynamic.dbcache.delete.DeleteAreaQueryDb;
import dmt.dynamic.dbcache.select.SelectAreaQueryDb;
import dmt.dynamic.dbcache.update.UpdateAreaQueryDb;

public class VolatileCacheDb implements  VolatileCache{

    private final Map<String, List<Object>> database;

    public VolatileCacheDb(){
        database = new ConcurrentHashMap<>();
    }

    @Override
    public <T> SelectAreaQuery<T> select(Class<T> element) {
        return new SelectAreaQueryDb<T>(database, element);
    }

    @Override
    public <T> UpdateAreaQuery<T> update(Class<T> element) {
        return new UpdateAreaQueryDb<>(database, element);
    }

    @Override
    public InsertAreaQuery insert(Object entity) {
        return new InsertAreaQuery() {

            @Override
            public void into(String area) {
                List<Object> table = new ArrayList<>();
                if(database.containsKey(area)){
                    table = database.getOrDefault(area, table);
                }
                table.add(entity);
                database.put(area, table);
            }

            @Override
            public void into() {
                into("default");
            } 
        };
    }

    @Override
    public <T> DeleteAreaQuery<T> delete(Class<T> element) {
        return new DeleteAreaQueryDb<>(database, element);
    }

    @Override
    public void drop(String area) {
        database.remove(area);
    }

    @Override
    public void drop() {
        database.clear();
    }

    @Override
    public String toString() {
        return "VolateliCache [database=" + database + "]";
    }

}

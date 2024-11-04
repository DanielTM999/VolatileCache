package dmt.dynamic.core;

import dmt.dynamic.core.delete.DeleteAreaQuery;
import dmt.dynamic.core.insert.InsertAreaQuery;
import dmt.dynamic.core.select.SelectAreaQuery;
import dmt.dynamic.core.update.UpdateAreaQuery;

public interface VolatileCache {
   <T> SelectAreaQuery<T> select(Class<T> element);
   <T> UpdateAreaQuery<T> update(Class<T> element);
   InsertAreaQuery insert(Object entity);
   <T> DeleteAreaQuery<T> delete(Class<T> element);
   void drop(String area);
   void drop();
}

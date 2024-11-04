package dtm.dynamic.core;

import dtm.dynamic.core.delete.DeleteAreaQuery;
import dtm.dynamic.core.insert.InsertAreaQuery;
import dtm.dynamic.core.select.SelectAreaQuery;
import dtm.dynamic.core.update.UpdateAreaQuery;

public interface VolatileCache {
   <T> SelectAreaQuery<T> select(Class<T> element);
   <T> UpdateAreaQuery<T> update(Class<T> element);
   InsertAreaQuery insert(Object entity);
   <T> DeleteAreaQuery<T> delete(Class<T> element);
   void drop(String area);
   void drop();
}

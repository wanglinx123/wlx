package relationQuery;

import java.lang.reflect.Field;

public interface IRelationSqlGenerator {
  <T> String one2ManySql(T obj, Field field);
  <T> String many2OneSql(T obj, Field field);
  <T> String one2OneSql(T obj, Field field);
}

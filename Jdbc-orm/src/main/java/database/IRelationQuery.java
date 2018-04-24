package database;

import java.lang.reflect.Field;
import java.util.List;

public interface IRelationQuery {
  <T> List<T> queryOne2Many(T obj, Field field);
  <T> T queryMany2One(T obj, Field field);
  <T> T queryOne2One(T obj, Field field);
}

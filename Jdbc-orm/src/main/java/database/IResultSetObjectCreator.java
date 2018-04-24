package database;

import java.sql.ResultSet;
import java.util.List;
import enumeration.EntityRelationEnum;

public interface IResultSetObjectCreator {
  <T> List<T> createResultSetObjecs(ResultSet rs, Class<T> clz) ;
  <T> Object createRelationResultSetObjects(ResultSet rs, Class<T> clz, EntityRelationEnum relationEnum);
}

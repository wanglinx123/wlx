package database;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import enumeration.EntityRelationEnum;
import exception.ResultSetInjectionException;
import reflection.MethodReflection;
import sqlBuilder.SqlParams;

public class IResultSetObjectCreatorImpl implements IResultSetObjectCreator {

  /**
   * inject object if has relation, execute a second query and injection
   *
   * @throws SQLException
   */
  @Override
  public <T> List<T> createResultSetObjecs(ResultSet resultSet, Class<T> clz) {
    Field[] fields = clz.getDeclaredFields();
    List<T> result = new ArrayList<>();

    try (ResultSet rs = resultSet){
      while (rs.next()) {
        T obj = clz.newInstance();

        for (Field field : fields) {
          if (SqlParams.isRelationField(field)) continue;
          String fieldName = field.getName();
          Object value = rs.getObject(fieldName);
          MethodReflection.setValue(obj, fieldName, value);
        }

        result.add(obj);
      }
    } catch (Exception e) {
      throw new ResultSetInjectionException(clz.getName());
    }

    return result.size() == 0 ? null : result;
  }

  @Override
  public <T> Object createRelationResultSetObjects(
      ResultSet rs, Class<T> clz, EntityRelationEnum relationEnum) {
    List<T> result = createResultSetObjecs(rs, clz);
    if(result == null) return result;
    if(relationEnum == EntityRelationEnum.One2One || relationEnum == EntityRelationEnum.Many2One) {
     return result.size() == 1 ? result.get(0) : null;
    }
    return result;
  }
}

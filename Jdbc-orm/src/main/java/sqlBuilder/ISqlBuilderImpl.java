package sqlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import conainer.Pair;

public class ISqlBuilderImpl{
  
  public <T> String selectSql(T obj) throws Exception {
    Map<String, String> map = SqlParams.nonNullParams(obj);

    StringBuilder sb =
        new StringBuilder("select * from ")
            .append(SqlParams.getTableName(obj))
            .append(" where ")
            .append(keyValSqlParams(map, " and "))
            .append(" 1 = 1");

    return sb.toString();
  }
  
  public <T> String deleteSql(T obj) throws Exception{
    Pair<String, Object> idPair = SqlParams.getPrimaryKey(obj);
    
    StringBuilder sb = new StringBuilder("delete from ")
            .append(SqlParams.getTableName(obj))
            .append(" where ")
            .append(idPair.getFirst()).append("=").append(idPair.getSecond());
    
    return sb.toString();
  }

  public <T> String updateSql(T obj) throws Exception {
    Map<String, String> map = SqlParams.nonRelationParams(obj);
    String keyVal = keyValSqlParams(map, ",");
    Pair<String, Object> pkPair = SqlParams.getPrimaryKey(obj);
    String pkName = pkPair.getFirst();
    Object pkVal = pkPair.getSecond();
    Objects.requireNonNull(pkVal, "primary key cannot be null in update sql");

    StringBuilder sb =
        new StringBuilder("update ")
            .append(obj.getClass().getSimpleName())
            .append(" set ")
            .append(keyVal.substring(0, keyVal.length() - 1))
            .append(" where ")
            .append(pkName).append("=").append(pkVal);
    return sb.toString();
  }
  
  public <T> String insertSql(T obj) throws Exception {
    Map<String, String> map = SqlParams.nonRelationParams(obj);
    StringBuilder params = new StringBuilder(" (");
    StringBuilder values = new StringBuilder(" values (");
    
    map.entrySet().forEach(e -> {
      params.append(e.getKey()).append(",");
      values.append(e.getValue()).append(",");
    });
    
    String p = params.toString().replaceAll(",$", ")");
    String v = values.toString().replaceAll(",$", ")");
    
    StringBuilder sb =
        new StringBuilder("insert into ")
            .append(SqlParams.getTableName(obj))
            .append(p).append(v);
    
    return sb.toString();
  }
  

  private String keyValSqlParams(Map<String, String> map, String joiner) {
    StringBuilder sb = new StringBuilder();
    map.entrySet()
        .forEach(
            e -> {
              sb.append(e.getKey()).append("=").append(e.getValue()).append(joiner);
            });
    return sb.toString();
  }
}

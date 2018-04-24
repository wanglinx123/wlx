package relationQuery;

import java.lang.reflect.Field;
import conainer.Pair;
import reflection.FieldReflction;
import sqlBuilder.SqlParams;

public class IRelationSqlGeneratorImpl implements IRelationSqlGenerator{

  @Override
  public <T> String one2ManySql(T obj, Field field) {
    String column = SqlParams.getJoinColumn(field);
    String oneIdVal = SqlParams.getPrimaryKeyValue(obj);
    Class<?> refClz = FieldReflction.getGenericType(field);
    String joinTable = SqlParams.getTableName(refClz);

    StringBuilder sb =
        new StringBuilder("select * from  ")
            .append(joinTable)
            .append(" where ")
            .append(column)
            .append(" = ")
            .append(oneIdVal);

    return sb.toString();
  }

  @Override
  public <T> String many2OneSql(T obj, Field field) {
    Class<?> refClz = field.getType();
    String joinPk = SqlParams.getPrimaryKeyName(refClz);
    String tarTable = SqlParams.getTableName(refClz);
    String srcTalbe = SqlParams.getTableName(obj);
    
    String fk = SqlParams.getJoinColumn(field);
    Pair<String, Object> pk =  SqlParams.getPrimaryKey(obj);
    
    //select tar.*
    // from a src inner join b tar
    //on src.tarid = tar.id
    //where src.id = ?

    StringBuilder sb =
        new StringBuilder("select tar.* from  ")
            .append(srcTalbe).append(" src inner join ")
            .append(tarTable).append(" tar on src.").append(fk)
            .append("=tar.").append(joinPk)
            .append(" where src.").append(pk.getFirst()).append("=").append(pk.getSecond().toString());

    return sb.toString();
  }
  
  @Override
  public <T> String one2OneSql(T obj, Field field) {
    Class<?> tarClz = field.getType();
    String tarTable = SqlParams.getTableName(tarClz);
    String srcTalbe = SqlParams.getTableName(obj);
    
    String fkTable = SqlParams.getJoinTable(field);
    if(fkTable == null) fkTable = srcTalbe;
    
    String fk = SqlParams.getJoinColumn(field);
    Pair<String, Object> srcIdPair = SqlParams.getPrimaryKey(obj);
    String srcId = srcIdPair.getFirst();
    
    StringBuilder sb = new StringBuilder("select tar.* from ")
        .append(srcTalbe).append(" src inner join ")
        .append(tarTable).append(" tar on src.");
    
    //fk can be in either src table or tar table
    if(srcTalbe.equalsIgnoreCase(fkTable)) {
      sb.append(fk).append("=tar.").append(SqlParams.getPrimaryKeyName(tarClz));
    }else {
      sb.append(srcId).append("=tar.").append(fk);
    }
    sb.append(" where src.").append(srcId).append("=").append(srcIdPair.getSecond());
    return sb.toString();
  }
}

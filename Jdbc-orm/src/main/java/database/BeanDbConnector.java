package database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import enumeration.EntityRelationEnum;
import enumeration.SqlTypeEnum;
import reflection.FieldReflction;
import relationQuery.RelationSqlBuilderFacetFacet;
import static enumeration.SqlTypeEnum.*;
import sqlBuilder.SqlBuilderFacet;
import sqlBuilder.SqlParams;

public class BeanDbConnector extends DbConnector implements IDbVisitor {

  private IResultSetObjectCreator rsObjCreator = new IResultSetObjectCreatorImpl();
  private SqlBuilderFacet sqlBuilder = new SqlBuilderFacet();
  private RelationSqlBuilderFacetFacet relationSqlBuilder = new RelationSqlBuilderFacetFacet();
  private RelationObjectInjector injector = new RelationObjectInjector();

  public <T> T selectOne(T obj) {
    List<T> result = select(obj);
    if (result == null) return null;
    return result.size() == 1 ? result.get(0) : null;
  }

  public <T> List<T> selectAll(Class<T> clz){
    try {
      return select(clz.newInstance());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Override
  public <T> List<T> select(T obj) {
    Connection conn = getConnection();
    Class<T> objClz = (Class<T>) obj.getClass();
    String sql = sqlBuilder.getSql(obj, SELECT);
    System.out.println(sql);
    ResultSet rs = query(conn, sql);
    List<T> result = (List<T>) rsObjCreator.createResultSetObjecs(rs, objClz);
    if (result == null) return null;
    // query @Relation fields
    queryRelationFields(result, objClz, conn);
    return result;
  }

  //do: get all relation fields and (do sth.) for each relation field.
  private <T> void queryRelationFields(List<T> objs, Class<T> objClz, Connection conn) {
    List<Field> relationFields = SqlParams.relationFields(objClz);

    for (Field eachField : relationFields) {
      handleSingleRelation(objs, eachField, conn);
    }
  }

  private <T> void handleSingleRelation(List<T> objs, Field relationField, Connection conn) {
    EntityRelationEnum relationEnum = SqlParams.getRelationTypeEnum(relationField);
    for (T eachObj : objs) {
      //query
      String relationSql =
          relationSqlBuilder.getRelationQuerySql(eachObj, relationField, relationEnum);
      System.out.println(relationSql ); 
      ResultSet relationRs = query(conn, relationSql);
      //create object
      Object relationObj =
          rsObjCreator.createRelationResultSetObjects(
              relationRs, FieldReflction.getBeanTypeOfField(relationField), relationEnum);
      //inject
      injector.injectObject(eachObj, relationField, relationObj);
    }
  }

  @Override
  public <T> int insert(T obj) {
    return updateDb(obj, INSERT);
  }

  @Override
  public <T> int delete(T obj) {
    return updateDb(obj, DELETE);
  }

  @Override
  public <T> int update(T obj) {
    return updateDb(obj, UPDATE);
  }

  private <T> int updateDb(T obj, SqlTypeEnum sqlType) {
    String sql = sqlBuilder.getSql(obj, sqlType);
    System.out.println(sql);
    return executeUpdate(getConnection(), sql);
  }
}

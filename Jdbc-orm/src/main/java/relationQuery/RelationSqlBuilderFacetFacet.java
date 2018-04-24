package relationQuery;

import java.lang.reflect.Field;
import enumeration.EntityRelationEnum;

public class RelationSqlBuilderFacetFacet {

  private IRelationSqlGenerator relationSqlGenerator = new IRelationSqlGeneratorImpl();

  public <T> String getRelationQuerySql(T obj, Field field, EntityRelationEnum relation) {
    String relationSql = null;
    switch (relation) {
      case One2One:
        relationSql = relationSqlGenerator.one2OneSql(obj, field);
        break;
      case One2Many:
        relationSql = relationSqlGenerator.one2ManySql(obj, field);
        break;
      case Many2One:
        relationSql = relationSqlGenerator.many2OneSql(obj, field);
        break;
      default:
        throw new RuntimeException(relation + " not supported");
    }
    return relationSql;
  }
}

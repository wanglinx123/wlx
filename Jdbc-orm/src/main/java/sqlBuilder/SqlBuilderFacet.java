package sqlBuilder;

import enumeration.SqlTypeEnum;

public class SqlBuilderFacet implements ISqlBuilder {

  private ISqlBuilderImpl builder = new ISqlBuilderImpl();

  @Override
  public <T> String getSql(T obj, SqlTypeEnum sqlType) {
    String sql = null;
    try {

      switch (sqlType) {
        case SELECT:
          sql = builder.selectSql(obj);
          break;
        case UPDATE:
          sql = builder.updateSql(obj);
          break;
        case INSERT:
          sql = builder.insertSql(obj);
          break;
        case DELETE:
          sql = builder.deleteSql(obj);
          break;
        default:
          throw new RuntimeException("sql type not supported");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sql;
  }
}

package sqlBuilder;

import enumeration.SqlTypeEnum;

public interface ISqlBuilder {
  <T> String getSql(T obj, SqlTypeEnum sqlType);
}

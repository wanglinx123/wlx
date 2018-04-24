package database;

import java.util.List;

public interface IDbVisitor {
  <T> List<T> select(T t);

  <T> int update(T t);

  <T> int insert(T t);

  <T> int delete(T t);
}

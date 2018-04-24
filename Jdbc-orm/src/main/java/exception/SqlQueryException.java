package exception;

public class SqlQueryException extends RuntimeException{
  public SqlQueryException(String sql) {
    super(sql);
  }
}

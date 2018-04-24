package exception;

public class SqlUpdateException extends RuntimeException {
  public SqlUpdateException(String sql) {
    super(sql);
  }
}

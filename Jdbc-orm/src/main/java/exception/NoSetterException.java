package exception;

public class NoSetterException extends RuntimeException {
  public NoSetterException(String msg) {
    super("No Setter found for field : " + msg);
  }
}

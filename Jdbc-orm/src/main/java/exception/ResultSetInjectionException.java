package exception;

public class ResultSetInjectionException extends RuntimeException {

  public ResultSetInjectionException(String clz) {
    super("cannot inject result set into Class: " + clz);
  }
}

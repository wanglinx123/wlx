package exception;

public class NoGetterException extends RuntimeException {
  
  public NoGetterException(String msg) {
    super("No Getter found for field : " +  msg);
  }
}

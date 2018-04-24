package exception;

public class NoIdAnnotationException extends RuntimeException{
  
  public NoIdAnnotationException(String clz) {
    super("no @ID annotation found on Class: " + clz);
  }
}

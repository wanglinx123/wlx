package exception;

public class NoJoinColumnException extends RuntimeException{
  public NoJoinColumnException(String fieldName) {
    super("No Join Column Value found on field: " + fieldName);
  }
}

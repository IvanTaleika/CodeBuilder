package cb.core.exceptions;

public class CBGenerationException extends CBException {

  /**
   * 
   */
  private static final long serialVersionUID = -4575160758010384837L;
  public CBGenerationException() {
  }
  
  public CBGenerationException(String message) {
    super(message);
  }
  
  public CBGenerationException(String message, Throwable e) {
    super(message, e);
  }

}

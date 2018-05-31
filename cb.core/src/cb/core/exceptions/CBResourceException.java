package cb.core.exceptions;

public class CBResourceException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = 7485735778131982388L;

  public CBResourceException() {
  }
  
  public CBResourceException(String message) {
    super(message);
  }
  
  public CBResourceException(String message, Throwable e) {
    super(message, e);
  }

}

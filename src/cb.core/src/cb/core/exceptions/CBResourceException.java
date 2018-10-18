package cb.core.exceptions;

public class CBResourceException extends CBException {

  /**
   * 
   */
  private static final long serialVersionUID = -6529164807719960654L;

  public CBResourceException() {
  }
  
  public CBResourceException(String message) {
    super(message);
  }
  
  public CBResourceException(String message, Throwable e) {
    super(message, e);
  }

}

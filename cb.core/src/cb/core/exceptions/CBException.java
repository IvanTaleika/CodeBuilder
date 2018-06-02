package cb.core.exceptions;

public class CBException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -2533412523195263820L;

  public CBException() {
  }
  
  public CBException(String message) {
    super(message);
  }
  
  public CBException(String message, Throwable e) {
    super(message, e);
  }


}

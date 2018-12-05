package by.bsuir.cb.design.code;

public interface IGenerative {

  /**
   * Generate Java code as a String.
   *
   * @return the string of Java code.
   */
  String toCodeString();

  /**
   * Returns parent of this object if present.
   *
   * @return object's parent if present, {@code null} otherwise
   */
  IGenerative getParent();


  void setParent(IGenerative parent);

  /**
   * Indicates if object was modified.
   * 
   * @return {@code true} if object is modified, {@code false} otherwise.
   */
  boolean isDirty();

  void setDirty(boolean dirty);
}

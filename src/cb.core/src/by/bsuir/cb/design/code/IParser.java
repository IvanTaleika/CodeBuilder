package by.bsuir.cb.design.code;

public interface IParser {
  // TODO change exception type
  int getInsertPosition() throws CbGenerationException;

  void insertCode(int position, String code) throws CbGenerationException;

}

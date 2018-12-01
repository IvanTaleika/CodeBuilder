package by.bsuir.cb.design.code;

import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;

public class Parser implements IParser {
  private ICompilationUnit compilationUnit;

  public Parser(ICompilationUnit compilationUnit) {
    this.compilationUnit = compilationUnit;
  }

  @Override
  public int getInsertPosition() throws CbGenerationException {
    try {
      IBuffer buffer = compilationUnit.getBuffer();
      ISourceRange range = compilationUnit.getSourceRange();
      int start = range.getOffset();
      int length = range.getLength();
      IScanner scanner;
      scanner = ToolFactory.createScanner(false, false, false, false);
      scanner.setSource(buffer.getCharacters());
      scanner.resetTo(start, start + length - 1);
      int token;
      int pos = 0;
      while ((token = scanner.getNextToken()) != ITerminalSymbols.TokenNameEOF) {
        if (token == ITerminalSymbols.TokenNameRBRACE) {
          pos = scanner.getCurrentTokenStartPosition();
        }
      }
      return pos;
    } catch (JavaModelException | InvalidInputException e) {
      throw new CbGenerationException(e);
    }
  }

  // @Override
  @Override
  public void insertCode(int position, String code) throws CbGenerationException {

    try {
      compilationUnit.getBuffer().replace(position, 0, code);
    } catch (JavaModelException e) {
      throw new CbGenerationException(e);
    }

  }

}

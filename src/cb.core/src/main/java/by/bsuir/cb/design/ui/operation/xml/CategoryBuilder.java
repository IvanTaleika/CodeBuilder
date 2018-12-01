package by.bsuir.cb.design.ui.operation.xml;

import by.bsuir.cb.design.ui.operation.Category;
import java.io.InputStream;
import java.util.List;

public interface CategoryBuilder {
  /**
   * Build {@link Category} list from XML file.
   *
   * @param xml source file
   */
  void buildCategories(InputStream xml) throws XmlParsingException;

  List<Category> getCategories();
}

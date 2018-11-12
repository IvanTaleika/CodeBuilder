package by.bsuir.cb.utils;

import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XMLParseUtils {
  public static LinkedList<Element> getElements(NodeList source) {
    LinkedList<Element> elements = new LinkedList<>();
    for (int i = 0; i < source.getLength(); i++) {
      if (source.item(i).getNodeType() == Node.ELEMENT_NODE) {
        elements.add((Element) source.item(i));
      }
    }
    return elements;
  }

  private XMLParseUtils() {}
}

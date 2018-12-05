package by.bsuir.cb.design.code.node;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants for node types.
 */
public enum NodeType {
  END("end"),
  TEMPLATE("template"),
  CONDITION("condition");

  /**
   * XML tag or attribute value.
   */
  private String xmlValue;

  /**
   * Contains xml string values with corresponding enum values.
   */
  private static final Map<String, NodeType> VALUES_MAP;

  static {
    VALUES_MAP = new HashMap<>();
    for (NodeType enumValue : NodeType.values()) {
      VALUES_MAP.put(enumValue.xmlValue, enumValue);
    }
  }

  /**
   * Constructs gem's XML parsing constant with the given string value.
   *
   * @param xmlStringValue the xmlValue to set.
   */
  NodeType(final String xmlStringValue) {
    xmlValue = xmlStringValue;
  }

  public String getXmlValue() {
    return xmlValue;
  }

  public static NodeType getEnumValue(final String xmlStringValue) {
    return VALUES_MAP.get(xmlStringValue);
  }
}

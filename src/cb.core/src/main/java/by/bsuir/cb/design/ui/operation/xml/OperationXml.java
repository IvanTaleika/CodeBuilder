package by.bsuir.cb.design.ui.operation.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains operation's XSD tag and attributes values for parsing.
 */
public enum OperationXml {
  ROOT_TAG("operationPicker"),
  CATEGORY("category"),
  CATEGORY_TITLE("title"),
  OPERATION("operation"),
  OPERATION_NAME("name"),
  NODE("node"),
  TYPE("type"),
  KEYWORDS("keywords"),
  KEYWORD("keyword"),
  KEYWORD_NAME("keywordName"),
  KEYWORD_TYPE("keywordType"),
  TEMPLATE("template"),
  DESCRIPTION("description"),
  IMAGE_PATH("iconPath");

  /**
   * XML tag or attribute value.
   */
  private String xmlValue;

  /**
   * Contains xml string values with corresponding enum values.
   */
  private static final Map<String, OperationXml> VALUES_MAP;

  static {
    VALUES_MAP = new HashMap<>();
    for (OperationXml enumValue : OperationXml.values()) {
      VALUES_MAP.put(enumValue.xmlValue, enumValue);
    }
  }

  /**
   * Constructs gem's XML parsing constant with the given string value.
   *
   * @param xmlStringValue the xmlValue to set.
   */
  OperationXml(final String xmlStringValue) {
    xmlValue = xmlStringValue;
  }

  public String getXmlValue() {
    return xmlValue;
  }

  public static OperationXml getEnumValue(final String xmlStringValue) {
    return VALUES_MAP.get(xmlStringValue);
  }
}

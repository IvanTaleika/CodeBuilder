package by.bsuir.cb.design.ui.operation.xml;

import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.Keyword;
import by.bsuir.cb.design.code.node.BeginNode;
import by.bsuir.cb.design.code.node.ConditionNode;
import by.bsuir.cb.design.code.node.EndNode;
import by.bsuir.cb.design.code.node.NodeType;
import by.bsuir.cb.design.code.node.TemplateNode;
import by.bsuir.cb.design.ui.method.TreeViewDecorator;
import by.bsuir.cb.design.ui.operation.Category;
import by.bsuir.cb.design.ui.operation.Operation;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class OperationXmlBuilder implements CategoryBuilder {
  private static final String XML_FORM_EXCEPTION = "XML file is not well formed";
  private final XMLInputFactory factory;
  private List<Category> categories;

  /**
   * Constructs default StAXBuilder.
   */
  public OperationXmlBuilder() {
    factory = XMLInputFactory.newInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildCategories(final InputStream xml) throws XmlParsingException {
    categories = new LinkedList<>();
    XMLStreamReader reader = null;
    try {
      reader = factory.createXMLStreamReader(xml);
      while (reader.hasNext()) {
        if (reader.next() == XMLStreamConstants.START_ELEMENT) {
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case CATEGORY:
              categories.add(buildCategory(reader));
              break;
            case ROOT_TAG:
              break;
            default:
              break;
          }
        }
      }
    } catch (final XMLStreamException e) {
      throw new XmlParsingException("StAX builder: " + e.getMessage(), e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (final XMLStreamException e) {
          throw new RuntimeException(e.getMessage(), e);
        }
      }
    }
  }

  private Category buildCategory(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    var category = new Category();
    List<Operation> operations = new LinkedList<>();
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case CATEGORY_TITLE:
              category.setTitle(getTextData(reader));
              break;
            case OPERATION:
              operations.add(buildOperation(reader));
              break;
            default:
              break;
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          if (OperationXml.getEnumValue(reader.getLocalName()) == OperationXml.CATEGORY) {
            category.setOperations(operations);
            return category;
          }
          break;
        default:
          break;
      }
    }
    throw new XmlParsingException(XML_FORM_EXCEPTION);
  }

  private Operation buildOperation(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    var operation = new Operation();
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case OPERATION_NAME:
              operation.setName(getTextData(reader));
              break;
            case NODE:
              operation.setMethodNode(buildMethodNode(reader));
              break;
            case DESCRIPTION:
              operation.setDescription(getTextData(reader));
              break;
            case IMAGE_PATH:
              operation.setIconPath(getTextData(reader));
              break;
            default:
              break;
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          if (OperationXml.getEnumValue(reader.getLocalName()) == OperationXml.OPERATION) {
            return operation;
          }
          break;
        default:
          break;
      }
    }
    throw new XmlParsingException(XML_FORM_EXCEPTION);
  }

  private IGenerative buildMethodNode(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    TemplateNode node = null;
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case TYPE:
              switch (NodeType.getEnumValue(getTextData(reader))) {
                case CONDITION:
                  node = new ConditionNode(null);
                  break;
                case END:
                  node = new EndNode(null);
                  break;
                case TEMPLATE:
                  node = new TemplateNode(null);
                  break;
                default:
                  break;
              }
              break;
            case KEYWORDS:
              node.setKeywordMap(buildKeywords(reader));
              break;
            case TEMPLATE:
              node.setTemplate(getTextData(reader));
              break;
            default:
              break;
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          if (OperationXml.getEnumValue(reader.getLocalName()) == OperationXml.NODE) {
            if (node instanceof ConditionNode) {
              int index = 1;
              for (Entry<String, IGenerative> entry : ((ConditionNode) node).getKeywordMap()
                  .entrySet()) {
                if (((Keyword) entry.getValue()).getType().equals("conditionResult")) {
                  var decorator = new TreeViewDecorator();
                  decorator.setIconPath("operations/begin_end.gif");
                  decorator.setName("Begin" + index);
                  index++;
                  var beginNode = new BeginNode(node);
                  decorator.setSource(beginNode);
                  ((ConditionNode) node).appendChild(decorator);
                  entry.setValue(beginNode);
                }
              }
            }
            return node;
          }
          break;
        default:
          break;
      }
    }
    throw new XmlParsingException(XML_FORM_EXCEPTION);
  }

  private Map<String, IGenerative> buildKeywords(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    var keyword = new Keyword();
    Map<String, IGenerative> keywords = new LinkedHashMap<>();
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case KEYWORD:
              keyword = new Keyword();
              break;
            case KEYWORD_NAME:
              keyword.setName(getTextData(reader));
              break;
            case KEYWORD_TYPE:
              keyword.setType(getTextData(reader));
              break;
            default:
              break;
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case KEYWORDS:
              return keywords;
            case KEYWORD:
              keywords.put(keyword.getName(), keyword);
              break;
            default:
              break;
          }
          break;
        default:
          break;
      }
    }
    throw new XmlParsingException(XML_FORM_EXCEPTION);
  }

  /**
   * Returns text data of XML Element.
   *
   * @param reader {@link XMLStreamReader} that is on the XML element that contains text data.
   * @return element's text data
   * @throws XmlParsingException if XML file is invalid against XSD
   * @throws XMLStreamException if fatal error occurred during the parsing.
   */
  private String getTextData(final XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    if (reader.hasNext()) {
      reader.next();
      return reader.getText();
    }
    throw new XmlParsingException("XML file is invalid against XSD");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Category> getCategories() {
    return categories;
  }



}

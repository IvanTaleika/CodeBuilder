package by.bsuir.cb.design.ui.operation.xml;

import by.bsuir.cb.design.code.node.MethodNode;
import by.bsuir.cb.design.ui.BundleResourceProvider;
import by.bsuir.cb.design.ui.operation.Category;
import by.bsuir.cb.design.ui.operation.IOperation;
import by.bsuir.cb.design.ui.operation.OperationButton;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxBuilder implements CategoryBuilder {
  private final XMLInputFactory factory;
  private List<Category> categories;

  /**
   * Constructs default StAXBuilder.
   */
  public StaxBuilder() {
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
            // throw new XmlParsingException("XML file is invalid against operation's XSD");
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
          throw new RuntimeException("Impossible to close StAX input stream: " + e.getMessage(), e);
        }
      }
    }
  }

  private Category buildCategory(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    var category = new Category();
    List<IOperation> operations = new LinkedList<>();
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
            // throw new XmlParsingException("XML file is invalid against operation's XSD");
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
    throw new XmlParsingException("XML file is not well formed");
  }

  private IOperation buildOperation(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    var operation = new OperationButton();
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case OPERATION_NAME:
              operation.setName(getTextData(reader));
              break;
            case NODE:
              buildMethodNode(reader);
              // operation.setNode(buildMethodNode(reader));
              break;
            case TOOLTIP:
              operation.setTooltip(getTextData(reader));
              break;
            case IMAGE_PATH:
              // FIXME delete BundleResourceProvider
              operation.setIcon(BundleResourceProvider.getImage(getTextData(reader)));
              break;
            default:
              break;
            // throw new XmlParsingException("XML file is invalid against operation's XSD");
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
    throw new XmlParsingException("XML file is not well formed");
  }


  // FIXME
  private MethodNode buildMethodNode(XMLStreamReader reader)
      throws XMLStreamException, XmlParsingException {
    while (reader.hasNext()) {
      switch (reader.next()) {
        case XMLStreamConstants.START_ELEMENT:
          switch (OperationXml.getEnumValue(reader.getLocalName())) {
            case TYPE:
              break;
            case KEYWORDS:
              break;
            case KEYWORD:
              break;
            case TEMPLATE:
              break;
            default:
              break;
            // throw new XmlParsingException("XML file is invalid against operation's XSD");
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          if (OperationXml.getEnumValue(reader.getLocalName()) == OperationXml.NODE) {
            return null;
          }
          break;
        default:
          break;
      }
    }
    throw new XmlParsingException("XML file is not well formed");
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

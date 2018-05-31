package cb.core.ui.utils;

public final class TemplatePathProvider {
  private static final String javaTemplatePath = "resources/operations/operationsJava.xml";
  
  public static String getTemplateClasspath() {
    //TODO check source language
    return javaTemplatePath;
  }
  
  private TemplatePathProvider() {
    
  }
}

package by.bsuir.cb.utils;

public final class PathProvider {
  private static final String javaTemplatePath = "resources/operations/operationsJava.xml";
  
  public static String getTemplateClasspath() {
    //TODO check source language
    return javaTemplatePath;
  }
  
  private PathProvider() {
    
  }
}

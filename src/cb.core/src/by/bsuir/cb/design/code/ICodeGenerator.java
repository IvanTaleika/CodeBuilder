package by.bsuir.cb.design.code;

import java.util.HashMap;

public interface ICodeGenerator {
  String generate(String template, HashMap<String, String> values);
}

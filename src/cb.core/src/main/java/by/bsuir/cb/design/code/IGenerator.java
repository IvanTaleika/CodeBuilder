package by.bsuir.cb.design.code;

import by.bsuir.cb.design.code.method.IMethod;

public interface IGenerator {
  String generateCode(IMethod method) throws CbGenerationException;
}

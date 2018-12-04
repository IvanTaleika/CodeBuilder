package by.bsuir.cb.design.code;

import by.bsuir.cb.design.code.method.IMethodTemp;

public interface IGenerator {
  String generateCode(IMethodTemp method) throws CbGenerationException;
}

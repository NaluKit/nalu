package com.github.nalukit.nalu.processor.model.intern;

public class ParameterAcceptor {

  private String parameterName;

  private String methodName;

  public ParameterAcceptor(String parameterName,
                           String methodName) {
    this.parameterName = parameterName;
    this.methodName = methodName;
  }

  public String getParameterName() {
    return parameterName;
  }

  public String getMethodName() {
    return methodName;
  }
}

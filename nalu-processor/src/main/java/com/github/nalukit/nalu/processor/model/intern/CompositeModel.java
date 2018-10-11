package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeModel {

  private ClassNameModel provider;

  private ClassNameModel componentInterface;

  private ClassNameModel component;

  private List<ParameterAcceptor> parameterAcceptors;

  public CompositeModel() {
  }

  public CompositeModel(ClassNameModel provider,
                        ClassNameModel componentInterface,
                        ClassNameModel component) {
    this.provider = provider;
    this.componentInterface = componentInterface;
    this.component = component;

    this.parameterAcceptors = new ArrayList<>();
  }

  public ClassNameModel getProvider() {
    return provider;
  }

  public void setProvider(ClassNameModel provider) {
    this.provider = provider;
  }

  public ClassNameModel getComponentInterface() {
    return componentInterface;
  }

  public void setComponentInterface(ClassNameModel componentInterface) {
    this.componentInterface = componentInterface;
  }

  public ClassNameModel getComponent() {
    return component;
  }

  public void setComponent(ClassNameModel component) {
    this.component = component;
  }

  public List<ParameterAcceptor> getParameterAcceptors() {
    return parameterAcceptors;
  }

  public String getParameterAcceptors(String parameterName) {
    Optional<ParameterAcceptor> optional = this.parameterAcceptors.stream()
                                                                  .filter(a -> parameterName.equals(a.getParameterName()))
                                                                  .findFirst();
    return optional.map(ParameterAcceptor::getMethodName)
                   .orElse(null);
  }
}

package com.github.nalukit.nalu.client.internal;

public class CompositeControllerReference {

  private String controller;

  private String compositeName;

  private String composite;

  private String selector;

  public CompositeControllerReference(String controller,
                                      String compositeName,
                                      String composite,
                                      String selector) {
    this.controller = controller;
    this.compositeName = compositeName;
    this.composite = composite;
    this.selector = selector;
  }

  public String getController() {
    return controller;
  }

  public String getCompositeName() {
    return compositeName;
  }

  public String getComposite() {
    return composite;
  }

  public String getSelector() {
    return selector;
  }
}

package com.github.nalukit.nalu.client.internal;

public class SplitterControllerReference {

  private String controller;

  private String splitterName;

  private String splitter;

  private String selector;

  public SplitterControllerReference(String controller,
                                     String splitterName,
                                     String splitter,
                                     String selector) {
    this.controller = controller;
    this.splitterName = splitterName;
    this.splitter = splitter;
    this.selector = selector;
  }

  public String getController() {
    return controller;
  }

  public String getSplitterName() {
    return splitterName;
  }

  public String getSplitter() {
    return splitter;
  }

  public String getSelector() {
    return selector;
  }
}

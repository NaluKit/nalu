package com.github.nalukit.nalu.processor.model.intern;

public class ControllerSplitterModel {

  private String name;

  private ClassNameModel splitter;

  private String selector;

  public ControllerSplitterModel() {
  }

  public ControllerSplitterModel(String name,
                                 ClassNameModel splitter,
                                 String selector) {
    this.name = name;
    this.splitter = splitter;
    this.selector = selector;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getSplitter() {
    return splitter;
  }

  public void setSplitter(ClassNameModel splitter) {
    this.splitter = splitter;
  }

  public String getSelector() {
    return selector;
  }

  public void setSelector(String selector) {
    this.selector = selector;
  }
}

package com.github.mvp4g.nalu.plugin.gwt.client.selector;

public class SelectorProvider {

  private static SelectorProvider instance = new SelectorProvider();



  public static SelectorProvider getInstance() {
    return instance;
  }

  private SelectorProvider() {
  }
}

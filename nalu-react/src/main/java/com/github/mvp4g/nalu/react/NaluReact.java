package com.github.mvp4g.nalu.react;

import elemental2.dom.DomGlobal;

public class NaluReact {

  public static final String NALU_ID_ATTRIBUTE = "naliId";

  public static final String NALU_ID_BODY = "NaluBody";


  public static void log(String message) {
    if ("on".equals(System.getProperty("superdevmode", "off"))) {
      DomGlobal.window.console.log(message);
    }
  }
}

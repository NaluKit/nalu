package com.github.mvp4g.nalu.client;

import elemental2.dom.DomGlobal;

public class Nalu {

  public static final String NALU_ID_ATTRIBUTE = "id";

  public static final String NALU_ID_BODY = "NaluBody";

  public final static String NALU_PARAMETER = "/:";

  public final static String NALU_SLEDGE_REPLACEMENT = "--U972--";

  public static void log(String message) {
    if ("on".equals(System.getProperty("superdevmode", "off"))) {
      DomGlobal.window.console.log(message);
    }
  }
}

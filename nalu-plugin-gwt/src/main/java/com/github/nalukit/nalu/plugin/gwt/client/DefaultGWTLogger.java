package com.github.nalukit.nalu.plugin.gwt.client;

import com.github.nalukit.nalu.client.application.IsLogger;
import com.google.gwt.core.client.GWT;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultGWTLogger
    implements IsLogger {

  static final String INDENT = "..";

  public void log(String message,
                  int depth) {
    if ("on".equals(System.getProperty("superdevmode",
                                       "off"))) {
      GWT.log(createLog(message,
                        depth));
    }
  }

  private String createLog(String message,
                           int depth) {
    if (depth == 0) {
      return "Nalu-Logger -> " + message;
    } else {
      String indent = IntStream.range(0,
                                      depth)
                               .mapToObj(i -> INDENT)
                               .collect(Collectors.joining("",
                                                           "",
                                                           message));
      return "Nalu-Logger -> " + indent;
    }
  }
}

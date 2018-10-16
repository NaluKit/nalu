package com.github.nalukit.nalu.simpleapplication.client.logger;

import com.github.nalukit.nalu.client.application.IsLogger;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultLogger
    implements IsLogger {

  private static final String INDENT = "    ";

  public void log(String message,
                  int depth) {
    if ("on".equals(System.getProperty("superdevmode",
                                       "off"))) {
      System.out.println(createLog(message,
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

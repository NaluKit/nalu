package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.application.IsLogger;

/**
 * Default implementation of Nalu's logger.
 *
 * @author plcoirier
 */
public class MockLogger
  implements IsLogger {

  static final String INDENT = "    ";

  public void log(String message,
                  int depth) {
    // we do nothing!
  }
}


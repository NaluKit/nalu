package com.github.mvp4g.nalu.processor.common;

import com.github.mvp4g.nalu.client.application.IsLogger;

/**
 * Default implementation of Mvp4gLogger.
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


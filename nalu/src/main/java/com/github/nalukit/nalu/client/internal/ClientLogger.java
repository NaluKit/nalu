package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.application.IsLogger;
import com.github.nalukit.nalu.client.application.annotation.Debug;

public class ClientLogger {

  private static ClientLogger   instance     = new ClientLogger();
  /* debug enabled? */
  private        boolean        debugEnabled = false;
  /* logger */
  private        IsLogger       logger;
  /* log level */
  private        Debug.LogLevel logLevel;

  private ClientLogger() {
  }

  public static ClientLogger get() {
    if (instance == null) {
      instance = new ClientLogger();
    }
    return instance;
  }

  public void register(boolean debugEnabled,
                       IsLogger logger,
                       Debug.LogLevel logLevel) {
    this.debugEnabled = debugEnabled;
    this.logger = logger;
    this.logLevel = logLevel;
  }

  public void logDetailed(String message,
                          int depth) {
    if (this.debugEnabled) {
      if (Debug.LogLevel.DETAILED.equals(this.logLevel)) {
        if (logger != null) {
          logger.log(message,
                     depth);
        }
      }
    }
  }

  public void logSimple(String message,
                        int depth) {
    if (this.debugEnabled) {
      if (logger != null) {
        logger.log(message,
                   depth);
      }
    }
  }
}

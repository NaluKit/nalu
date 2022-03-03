package com.github.nalukit.nalu.client;

import java.io.IOException;
import java.util.Properties;

public class Version {
  private static volatile Version instance;
  private String version;

  public static Version getInstance() {
    if (instance == null) {
      synchronized (Version.class) {
        if (instance == null) {
          instance = new Version();
        }
      }
    }
    return instance;
  }

  private Version(){
    final Properties properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("version.properties"));
      this.version = properties.getProperty("artifact.version");
    } catch (IOException e) {
      e.printStackTrace();
      this.version = "VERSION-UNKNOWN";
    }


  }

  public String getVersion() {
    return this.version;
  }
}
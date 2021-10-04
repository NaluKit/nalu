package com.github.nalukit.nalu.client.util;

import java.util.Objects;

public class NaluUtils {
  
  private static NaluUtils instance;
  
  private NaluUtils() {
  }
  
  /**
   * Compares two routes.
   * <p>
   * Parameters of the route will be converted using @see naluUtils#convertRoute
   *
   * @param route01 first route of the compare
   * @param route02 second route of the compare
   * @return true in case the no parameter parts are equal otherwise false
   */
  public boolean compareRoutes(String route01,
                               String route02) {
    String convertedRoute01 = NaluUtils.get()
                                       .convertRoute(route01);
    String convertedRoute02 = NaluUtils.get()
                                       .convertRoute(route02);
    return convertedRoute01.equals(convertedRoute02);
  }
  
  /**
   * Converts the parameter parts with '*'
   * <p>
   * A route containing null will be converted to an empty string!
   *
   * @param route route to convert
   * @return converted route
   */
  @SuppressWarnings("StringSplitter")
  public String convertRoute(String route) {
    if (Objects.isNull(route)) {
      return "";
    }
    if ("/".equals(route)) {
      return route;
    }
    String[]      splits   = route.split("/");
    StringBuilder newRoute = new StringBuilder();
    for (int i = 1; i < splits.length; i++) {
      String s = splits[i];
      if (!Objects.isNull(s)) {
        if ("*".equals(s) || (s.startsWith("{") && s.endsWith("}"))) {
          newRoute.append("/*");
        } else if (s.startsWith(":") || (s.startsWith("{") && s.endsWith("}"))) {
          newRoute.append("/*");
        } else {
          newRoute.append("/")
                  .append(s);
        }
      }
    }
    return newRoute.toString();
  }
  
  public static NaluUtils get() {
    if (Objects.isNull(instance)) {
      instance = new NaluUtils();
    }
    return instance;
  }
  
}

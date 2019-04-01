package com.github.nalukit.nalu.client.util;

import java.util.Objects;

public class NaluUtils {

  /**
   * Converts the parameter parts with '*'
   * <p>
   * A route containing null will be converted to an empty string!
   *
   * @param route route to convert
   * @return converted route
   */
  public final static String convertRoute(String route) {
    if (Objects.isNull(route)) {
      return "";
    }
    if ("/".equals(route)) {
      return route;
    }
    String[] splits = route.split("/");
    String newRoute = "";
    for (int i = 1; i < splits.length; i++) {
      String s = splits[i];
      if (!Objects.isNull(s)) {
        if ("*".equals(s)) {
          newRoute += "/*";
        } else if (s.startsWith(":")) {
          newRoute += "/*";
        } else {
          newRoute += "/" + s;
        }
      }
    }
    return newRoute;
  }

  /**
   * Compares two routes.
   * <p>
   * Parameters of the route will be converted using @see NaluUilts#convertRoute
   *
   * @param route01 first route of the compare
   * @param route02 second route of the compare
   * @return true in case the non parameter parts are equal otherwise false
   */
  public final static boolean compareRoutes(String route01,
                                            String route02) {
    String convertedRoute01 = NaluUtils.convertRoute(route01);
    String convertedRoute02 = NaluUtils.convertRoute(route02);
    return convertedRoute01.equals(convertedRoute02);
  }

}

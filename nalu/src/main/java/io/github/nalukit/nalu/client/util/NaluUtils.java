package io.github.nalukit.nalu.client.util;

import io.github.nalukit.nalu.client.internal.NaluConfig;

import java.util.Objects;

public class NaluUtils {

  public final static NaluUtils INSTANCE = new NaluUtils();

  private NaluUtils() {
  }

  /**
   * Returns an instance of NaluUtils!
   * <b>Deprecated: please use NaluUtils.INSTANCE instead!</b>
   */
  @Deprecated
  public static NaluUtils get() {
    return NaluUtils.INSTANCE;
  }

  /**
   * Compares two routes.
   * <p>
   * Parameters of the route will be converted using @see naluUtils#convertRoute
   *
   * @param route01 first route of the compare
   * @param route02 second route of the compare
   * @return true in case the the parts of the route without parameters are equal otherwise false
   */
  public boolean compareRoutes(String route01,
                               String route02) {
    String convertedRoute01 = NaluUtils.INSTANCE.convertRoute(route01);
    String convertedRoute02 = NaluUtils.INSTANCE.convertRoute(route02);
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
        if ("*".equals(s) || this.isParameter(s)) {
          newRoute.append("/*");
        } else if (s.startsWith(":") || this.isParameter(s)) {
          newRoute.append("/*");
        } else {
          newRoute.append("/")
                  .append(s);
        }
      }
    }
    return newRoute.toString();
  }

  private boolean isParameter(String split) {
    return split.startsWith("{") && split.endsWith("}");
  }

  /**
   * cleans the route (f.e.: remove the hash) if the useHash-Property is false!
   *
   * @param route route to clean
   * @return cleaned route
   */
  public String cleanRoute(String route) {
    if (NaluConfig.INSTANCE.isUsingHash()) {
      return route;
    }
    if (Objects.isNull(route)) {
      return "";
    }
    String cleanedRoute = route;
    int    positionHash = route.indexOf("#");
    //    int positionQuestionMark = route.indexOf("?");
    if (positionHash > -1) {
      cleanedRoute = route.substring(0,
                                     positionHash);
    }
    return cleanedRoute;
  }

  /**
   * Removes the leading sign from the string if it is present.
   *
   * @param leadingSign the leading sign to be removed
   * @param string the string from which the leading sign should be removed
   * @return the string without the leading sign if it was present, otherwise the original string
   */
  public static String removeLeading(String leadingSign, String string) {
    if (string.startsWith(leadingSign)) {
      return string.substring(leadingSign.length());
    }
    return string;
  }

}

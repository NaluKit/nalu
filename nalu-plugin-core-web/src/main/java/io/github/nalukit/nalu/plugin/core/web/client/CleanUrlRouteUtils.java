package io.github.nalukit.nalu.plugin.core.web.client;

/**
 * Helper methods for clean URL (hash-less) routing. This class does not use any DOM
 * access, so it can be tested without a browser.
 */
public final class CleanUrlRouteUtils {

  private CleanUrlRouteUtils() {
  }

  /**
   * Removes leading and trailing slashes.
   *
   * @param value value to trim
   * @return the trimmed value (never null)
   */
  public static String trimSlashes(String value) {
    if (value == null) {
      return "";
    }
    int start = 0;
    int end   = value.length();
    while (start < end && value.charAt(start) == '/') {
      start++;
    }
    while (end > start && value.charAt(end - 1) == '/') {
      end--;
    }
    return value.substring(start,
                           end);
  }

  /**
   * Removes a prefix (context path / base path) from a path. The prefix is only removed
   * at a segment boundary: prefix "app" will not be removed from "application/x".
   *
   * @param path   the path
   * @param prefix the prefix (with or without slashes)
   * @return the path without the prefix. If the prefix was removed, the result starts with "/" (or is empty)
   */
  public static String removePrefix(String path,
                                    String prefix) {
    String cleanPrefix = trimSlashes(prefix);
    if (path == null) {
      return "";
    }
    if (cleanPrefix.isEmpty()) {
      return path;
    }
    String cleanPath = path.startsWith("/") ?
                       path.substring(1) :
                       path;
    if (cleanPath.equals(cleanPrefix)) {
      return "";
    }
    if (cleanPath.startsWith(cleanPrefix + "/")) {
      return cleanPath.substring(cleanPrefix.length());
    }
    return path;
  }

  /**
   * Extracts the route from a location pathname: removes the prefix, a trailing
   * html-file (f.e.: index.html) and leading/trailing slashes.
   *
   * @param pathname the pathname (f.e.: "/app/shell/route/")
   * @param prefix   the base path / context path (f.e.: "/app")
   * @return the route without leading and trailing slash (f.e.: "shell/route")
   */
  public static String extractRoute(String pathname,
                                    String prefix) {
    String path = trimSlashes(pathname);
    int    last = path.lastIndexOf('/');
    String lastSegment = last == -1 ?
                         path :
                         path.substring(last + 1);
    if (lastSegment.endsWith(".html")) {
      path = last == -1 ?
             "" :
             path.substring(0,
                            last);
    }
    return trimSlashes(removePrefix(path,
                                    prefix));
  }

  /**
   * Builds the path that is written into the browser url.
   *
   * @param prefix        base path / context path (with or without slashes, can be empty)
   * @param route         the route (with or without leading slash)
   * @param trailingSlash true: the result ends with a "/"
   * @return path starting with a "/"
   */
  public static String buildPath(String prefix,
                                 String route,
                                 boolean trailingSlash) {
    StringBuilder path = new StringBuilder();
    String        cleanPrefix = trimSlashes(prefix);
    String        cleanRoute  = trimSlashes(route);
    if (!cleanPrefix.isEmpty()) {
      path.append("/")
          .append(cleanPrefix);
    }
    if (!cleanRoute.isEmpty()) {
      path.append("/")
          .append(cleanRoute);
    }
    if (path.length() == 0) {
      return "/";
    }
    if (trailingSlash) {
      path.append("/");
    }
    return path.toString();
  }

}

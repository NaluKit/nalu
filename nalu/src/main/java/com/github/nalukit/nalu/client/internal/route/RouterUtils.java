package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.Nalu;

import java.util.Objects;

public class RouterUtils {
  
  private static RouterUtils instance;
  
  private RouterUtils() {
  }
  
  /**
   * This method compares the route with the value of withRoute respecting parameters.
   *
   * <ul>
   * <li>the route can contain parameter values</li>
   * <li>the withRoute should contain '*' instead of parameter values</li>
   * </ul>
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
   * will return true
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
   * will return false.
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
   * will return true.
   *
   * @param route     the route containing parameter values instead of '*'
   * @param withRoute the compare route which has no parameter values and uses '*' instead
   * @return true the routes matches or false in case not
   */
  public boolean match(String route,
                       String withRoute) {
    return RouterUtils.get()
                      .match(route,
                             withRoute,
                             false);
  }
  
  /**
   * This method compares the route with the value of withRoute respecting parameters.
   *
   * <ul>
   * <li>the route can contain parameter values</li>
   * <li>the withRoute should contain '*' instead of parameter values</li>
   * </ul>
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
   * will return true
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
   * will return false.
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
   * will return true.
   *
   * @param route     the route containing parameter values instead of '*'
   * @param withRoute the compare route which has no parameter values and uses '*' instead
   * @param exact     if true, routes must match exactly
   * @return true the routes matches or false in case not
   */
  public boolean match(String route,
                       String withRoute,
                       boolean exact) {
    String[] partsOfRoute = RouterUtils.get()
                                       .split(route);
    String[] partsOfWithRoute = RouterUtils.get()
                                           .split(withRoute);
    
    // in case route has more parts than withRoute, return false
    if (partsOfRoute.length > partsOfWithRoute.length) {
      return false;
    }
    // if numbers of parts not equal ==> routes do not match!
    if (exact) {
      if (partsOfRoute.length != partsOfWithRoute.length) {
        return false;
      }
    }
    // check if colons are used inside the url
    if (Nalu.isUsingColonForParametersInUrl()) {
      for (int i = 0; i < partsOfRoute.length; i++) {
        if (partsOfRoute[i].startsWith(":")) {
          if (partsOfWithRoute.length > i) {
            if (!"*".equals(partsOfWithRoute[i])) {
              return false;
            }
          } else {
            return false;
          }
        } else {
          if (partsOfWithRoute.length > i) {
            if (!partsOfRoute[i].equals(partsOfWithRoute[i])) {
              return false;
            }
          } else {
            return false;
          }
        }
        if (partsOfRoute.length - 1 == i) {
          if (partsOfWithRoute.length > partsOfRoute.length) {
            return RouterUtils.get()
                              .onlyParameterAddEnd(partsOfWithRoute,
                                                   i + 1);
          }
        }
      }
      //
      //      for (int i = 0; i < partsOfWithRoute.length; i++) {
      //        if (partsOfRoute.length < i + 1) {
      //          return RouterUtils.onlyParameterAddEnd(partsOfWithRoute,
      //                                                 i);
      //        }
      //        // check if partsOfRoute[i] startsWith ':' ==> partOfWithRoute[i] must be '*'
      //        if (Nalu.isUsingColonForParametersInUrl()) {
      //          if (partsOfRoute[i].startsWith(":")) {
      //            if (!"*".equals(partsOfWithRoute[i])) {
      //              return false;
      //            }
      //          } else {
      //            if (!partsOfWithRoute[i].equals(partsOfRoute[i])) {
      //              return false;
      //            }
      //          }
      //        } else {
      //          if (!partsOfWithRoute[i].equals(partsOfRoute[i])) {
      //            if (!"*".equals(partsOfWithRoute[i])) {
      //              return false;
      //            }
      //          }
      //        }
    } else {
      // compare the parts!
      for (int i = 0; i < partsOfRoute.length; i++) {
        if (!partsOfWithRoute[i].equals(partsOfRoute[i])) {
          if (!"*".equals(partsOfWithRoute[i])) {
            return false;
          }
        }
        if (partsOfRoute.length - 1 == i) {
          if (partsOfWithRoute.length > partsOfRoute.length) {
            return RouterUtils.get()
                              .onlyParameterAddEnd(partsOfWithRoute,
                                                   i + 1);
          }
        }
        //        if (partsOfWithRoute.length < i + 1) {
        //          return RouterUtils.parameterOnPosition(partsOfWithRoute,
        //                                                 i);
        //        } else {
        //          // check if partOfWithRoute[i] is '*' ==> everything is fine
        //          if (!"*".equals(partsOfWithRoute[i])) {
        //            if (partsOfRoute.length < i + 1) {
        //              if (!exact) {
        //                return RouterUtils.onlyParameterAddEnd(partsOfRoute,
        //                                                       i);
        //              }
        //              return false;
        //            } else {
        //              if (!partsOfWithRoute[i].equals(partsOfRoute[i])) {
        //                return false;
        //              }
        //            }
        //          }
        //        }
      }
    }
    return true;
  }
  
  public static RouterUtils get() {
    if (Objects.isNull(instance)) {
      instance = new RouterUtils();
    }
    return instance;
  }
  
  private String[] split(String route) {
    if (route.startsWith("/")) {
      if (route.length() > 1) {
        return route.substring(1)
                    .split("/");
      }
      return new String[] { "" };
    } else {
      return route.split("/");
    }
  }
  
  private boolean onlyParameterAddEnd(String[] partsOfRoute,
                                      int index) {
    for (int i = index; i < partsOfRoute.length; i++) {
      if (!"*".equals(partsOfRoute[i])) {
        return false;
      }
    }
    return true;
  }
  
}

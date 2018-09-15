package com.github.mvp4g.nalu.client;

import com.github.mvp4g.nalu.client.internal.ClientLogger;

import java.util.Arrays;
import java.util.stream.Stream;

class RouterLogger {

  RouterLogger() {
  }

  static void logHandleHash(String hash) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: handleRouting for hash ->>")
      .append(hash)
      .append("<<");
    logDetailed(sb.toString(),
                0);
  }

  static void logDetailed(String message,
                          int depth) {
    ClientLogger.get()
                .logDetailed(message,
                             depth);
  }

  static void logNoMatchingRoute(String hash,
                                 String routeErrorRoute) {
    StringBuilder sb = new StringBuilder();
    sb.append("no matching route for hash >>")
      .append(hash)
      .append("<< --> use configurated route: >")
      .append(routeErrorRoute)
      .append("<<");
    logSimple(sb.toString(),
              1);
  }

  static void logSimple(String message,
                        int depth) {
    ClientLogger.get()
                .logSimple(message,
                           depth);
  }

  static void logFilterInterceptsRouting(String canonicalName,
                                         String redirectTo,
                                         String[] parameters) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: filter >>")
      .append(canonicalName)
      .append("<< intercepts routing! New route: >>")
      .append(redirectTo)
      .append("<<");
    if (Arrays.asList(parameters)
              .size() > 0) {
      sb.append(" with parameters: ");
      Stream.of(parameters)
            .forEach(p -> sb.append(">>")
                            .append(p)
                            .append("<< "));
    }
    logSimple(sb.toString(),
              1);
  }

  static void logControllerInterceptsRouting(String controllerClassName,
                                             String route,
                                             String[] parameter) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: create controller >>")
      .append(controllerClassName)
      .append("<< intercepts routing! New route: >>")
      .append(route)
      .append("<<");
    if (Arrays.asList(parameter)
              .size() > 0) {
      sb.append(" with parameters: ");
      Stream.of(parameter)
            .forEach(p -> sb.append(">>")
                            .append(p)
                            .append("<< "));
    }
    logSimple(sb.toString(),
              0);
  }

  static void logNoControllerFoundForHash(String hash) {
    StringBuilder sb = new StringBuilder();
    sb.append("no controller found for hash >>")
      .append(hash)
      .append("<<");
    logSimple(sb.toString(),
              1);
  }

  static void logUseErrorRoute(String routeErrorRoute) {
    StringBuilder sb = new StringBuilder();
    sb.append("use configurated default route >>")
      .append(routeErrorRoute)
      .append("<<");
    logSimple(sb.toString(),
              1);
  }

  static void logControllerOnAttachedMethodCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: create controller >>")
      .append(canonicalName)
      .append("<< - calls method onAttached()");
    logDetailed(sb.toString(),
                2);
  }

  static void logControllerStartMethodCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: create controller >>")
      .append(canonicalName)
      .append("<< - calls method start()");
    logDetailed(sb.toString(),
                2);
  }

  static void logShellOnAttachedComponentMethodCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: create controller >>")
      .append(canonicalName)
      .append("<< - calls shell.onAttachedComponent()");
    logDetailed(sb.toString(),
                2);
  }

  static void logControllerStopMethodWillBeCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("controller >>")
      .append(canonicalName)
      .append("<< --> will be stopped");
    logSimple(sb.toString(),
              1);
  }

  static void logControllerStopMethodCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("controller >>")
      .append(canonicalName)
      .append("<< --> stopped");
    logDetailed(sb.toString(),
                2);
  }

  static void logControllerDetached(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("controller >>")
      .append(canonicalName)
      .append("<< --> detached");
    logDetailed(sb.toString(),
                2);
  }

  static void logControllerRemoveHandlersMethodCalled(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("controller >>")
      .append(canonicalName)
      .append("<< --> removed handlers");
    logDetailed(sb.toString(),
                2);
  }

  static void logControllerStopped(String canonicalName) {
    StringBuilder sb = new StringBuilder();
    sb.append("controller >>")
      .append(canonicalName)
      .append("<< --> stopped");
    logSimple(sb.toString(),
              1);
  }

  static String logWrongNumbersOfPrameters(String hash,
                                           String route,
                                           int sizeParameterConfig,
                                           int sizeParameterRoute) {
    StringBuilder sb = new StringBuilder();
    sb.append("hash >>")
      .append(hash)
      .append("<< --> found routing >>")
      .append(route)
      .append("<< -> too much parameters! Expeted >>")
      .append(sizeParameterConfig)
      .append("<< - found >>")
      .append(sizeParameterRoute)
      .append("<<");
    RouterLogger.logSimple(sb.toString(),
                           1);
    return sb.toString();
  }

  static String logNoMatchingRoute(String hash) {
    StringBuilder sb = new StringBuilder();
    sb.append("no matching route for hash >>")
      .append(hash)
      .append("<< --> Routing aborted!");
    RouterLogger.logSimple(sb.toString(),
                           1);
    return sb.toString();
  }
}

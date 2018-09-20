package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.ClientLogger;

import java.util.Arrays;
import java.util.stream.Stream;

class RouterLogger {

  RouterLogger() {
  }

  static void logHandleHash(String hash) {
    String sb = "Router: handleRouting for hash ->>" +
                hash +
                "<<";
    logDetailed(sb,
                0);
  }

  private static void logDetailed(String message,
                                  int depth) {
    ClientLogger.get()
                .logDetailed(message,
                             depth);
  }

  static void logNoMatchingRoute(String hash,
                                 String routeErrorRoute) {
    String sb = "no matching route for hash >>" +
                hash +
                "<< --> use configurated route: >" +
                routeErrorRoute +
                "<<";
    logSimple(sb,
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
    String sb = "no controller found for hash >>" +
                hash +
                "<<";
    logSimple(sb,
              1);
  }

  static void logUseErrorRoute(String routeErrorRoute) {
    String sb = "use configurated default route >>" +
                routeErrorRoute +
                "<<";
    logSimple(sb,
              1);
  }

  static void logControllerOnAttachedMethodCalled(String canonicalName) {
    String sb = "Router: create controller >>" +
                canonicalName +
                "<< - calls method onAttached()";
    logDetailed(sb,
                2);
  }

  static void logControllerStartMethodCalled(String canonicalName) {
    String sb = "Router: create controller >>" +
                canonicalName +
                "<< - calls method start()";
    logDetailed(sb,
                2);
  }

  static void logShellOnAttachedComponentMethodCalled(String canonicalName) {
    String sb = "Router: create controller >>" +
                canonicalName +
                "<< - calls shell.onAttachedComponent()";
    logDetailed(sb,
                2);
  }

  static void logControllerStopMethodWillBeCalled(String canonicalName) {
    String sb = "controller >>" +
                canonicalName +
                "<< --> will be stopped";
    logSimple(sb,
              1);
  }

  static void logControllerStopMethodCalled(String canonicalName) {
    String sb = "controller >>" +
                canonicalName +
                "<< --> stopped";
    logDetailed(sb,
                2);
  }

  static void logControllerDetached(String canonicalName) {
    String sb = "controller >>" +
                canonicalName +
                "<< --> detached";
    logDetailed(sb,
                2);
  }

  static void logControllerRemoveHandlersMethodCalled(String canonicalName) {
    String sb = "controller >>" +
                canonicalName +
                "<< --> removed handlers";
    logDetailed(sb,
                2);
  }

  static void logControllerStopped(String canonicalName) {
    String sb = "controller >>" +
                canonicalName +
                "<< --> stopped";
    logSimple(sb,
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
    logSimple(sb.toString(),
              1);
    return sb.toString();
  }

  static String logNoMatchingRoute(String hash) {
    StringBuilder sb = new StringBuilder();
    sb.append("no matching route for hash >>")
      .append(hash)
      .append("<< --> Routing aborted!");
    logSimple(sb.toString(),
              1);
    return sb.toString();
  }

  static void logControllerLookForSplitter(String controller) {
    String sb = "controller >>" +
                controller +
                "<< --> looking for splitter";
    logDetailed(sb,
                2);
  }

  static void logControllerNoSplitterFound(String controller) {
    String sb = "controller >>" +
                controller +
                "<< --> no splitter found";
    logDetailed(sb,
                3);
  }

  static void logControllerSplitterFound(String controller,
                                         int numberofSplitterFound) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter found >>" +
                Integer.toString(numberofSplitterFound) +
                "<<";
    logDetailed(sb,
                3);
  }

  static void logSplitterInjectedInController(String controller,
                                              String splitter) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter >>" +
                splitter +
                "<< injected";
    logDetailed(sb,
                3);
  }

  static void logControllerOnAttachedSplitter(String controller,
                                              String splitter) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter >>" +
                splitter +
                "<< attached";
    logDetailed(sb,
                3);
  }

  static void logSplitterStartMethodCalled(String splitter) {
    String sb = "Router: create splitter >>" +
                splitter +
                "<< - calls method start()";
    logDetailed(sb,
                3);
  }

  static void logSplitterNotFound(String controller,
                                  String splitter) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter >>" +
                splitter +
                "<< not found";
    logDetailed(sb,
                3);
  }

  static void logFilterStopMethodWillBeCalled(String controller,
                                              String splitter) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter >>" +
                splitter +
                "<< stop method will be called";
    logDetailed(sb,
                3);
  }

  static void loFilterStopMethodCalled(String controller,
                                       String splitter) {
    String sb = "controller >>" +
                controller +
                "<< --> splitter >>" +
                splitter +
                "<< stop malled";
    logDetailed(sb,
                3);
  }
}

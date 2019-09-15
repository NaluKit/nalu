package com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.0.0-SNAPSHOT<< at >>2019.09.15-14:55:02<< */
public final class GenerateWithIsComponentCreatorImpl extends AbstractApplication<MockContext> implements GenerateWithIsComponentCreator {
  public GenerateWithIsComponentCreatorImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void logProcessorVersion() {
    ClientLogger.get().logDetailed("", 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    StringBuilder sb01 = new StringBuilder();
    sb01.append("Nalu processor version  >>2.0.0-SNAPSHOT<< used to generate this source");
    ClientLogger.get().logDetailed(sb01.toString(), 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    ClientLogger.get().logDetailed("", 0);
  }

  @Override
  public void loadDebugConfiguration() {
  }

  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  public void loadShells() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load shell references");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockShell"));
    sb01.setLength(0);
    sb01.append("register shell >>/mockShell<< with class >>com.github.nalukit.nalu.processor.common.MockShell<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }

  @Override
  public void loadShellFactory() {
    // create ShellCreator for: com.github.nalukit.nalu.processor.common.MockShell
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockShell", new com.github.nalukit.nalu.processor.common.MockShellCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    // create ControllerCreator for: com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator.ui.content01.Content01Controller
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator.ui.content01.Content01Controller", new com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator.ui.content01.Content01ControllerCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load routes");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator.ui.content01.Content01Controller"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route01<< with parameter >><< for selector >>selector01<< for controller >>com.github.nalukit.nalu.processor.controller.generateWithIsComponentCreator.ui.content01.Content01Controller<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }

  @Override
  public void loadPopUpControllerFactory() {
  }

  @Override
  public void loadErrorPopUpController() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("no ErrorPopUpController found!°");
  }

  @Override
  public void loadFilters() {
  }

  @Override
  public void loadHandlers() {
  }

  @Override
  public void loadCompositeReferences() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load composite references");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
  }

  @Override
  public void loadModules() {
    StringBuilder sb01 = new StringBuilder();
  }

  @Override
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    StringBuilder sb01 = new StringBuilder();
    this.startRoute = "/mockShell/route01";
    sb01.append("found startRoute >>/mockShell/route01<<");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    sb01.setLength(0);
    ClientLogger.get().logDetailed(sb01.toString(), 2);
  }

  @Override
  public boolean hasHistory() {
    return true;
  }

  @Override
  public boolean isUsingHash() {
    return true;
  }

  @Override
  public boolean isUsingColonForParametersInUrl() {
    return false;
  }

  @Override
  public boolean isStayOnSide() {
    return false;
  }

  @Override
  public PropertyFactory.ErrorHandlingMethod getErrorHandlingMethod() {
    return PropertyFactory.ErrorHandlingMethod.ROUTING;
  }
}
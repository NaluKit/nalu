package com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import java.lang.StringBuilder;
import java.util.Arrays;

public final class GenerateWithoutIsComponentCreatorImpl extends AbstractApplication<MockContext> implements GenerateWithoutIsComponentCreator {
  public GenerateWithoutIsComponentCreatorImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadDebugConfiguration() {
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockShell"));
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
    // create ControllerCreator for: com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller", new com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01ControllerCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller"));
  }

  @Override
  public void loadFilters() {
  }

  @Override
  public void loadHandlers() {
  }

  @Override
  public void loadCompositeReferences() {
  }

  @Override
  public void loadPlugins() {
    StringBuilder sb01 = new StringBuilder();
  }

  @Override
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/mockShell/route01";
    this.errorRoute = "/mockShell/route01";
  }
}

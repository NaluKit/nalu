package com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator;

import com.github.nalukit.nalu.client.application.IsLoader;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.2.1<< at >>2020.11.18-06:49:25<<
 */
public final class GenerateWithoutIsComponentCreatorImpl extends AbstractApplication<MockContext> implements GenerateWithoutIsComponentCreator {
  public GenerateWithoutIsComponentCreatorImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }
  
  @Override
  public void loadLoggerConfiguration() {
  }
  
  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>2.2.1<< used to generate this source").addMessage("=================================================================================").addMessage(""));
  }
  
  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }
  
  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockShell"));
  }
  
  @Override
  public void loadShellFactory() {
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockShell", new com.github.nalukit.nalu.processor.common.MockShellCreatorImpl(router, context, eventBus));
  }
  
  @Override
  public void loadCompositeController() {
  }
  
  @Override
  public void loadComponents() {
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller", new com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01ControllerCreatorImpl(router, context, eventBus));
  }
  
  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller"));
  }
  
  @Override
  public void loadBlockControllerFactory() {
  }
  
  @Override
  public void loadPopUpControllerFactory() {
  }
  
  @Override
  public void loadErrorPopUpController() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("no ErrorPopUpController found!"));
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
  public void loadModules() {
    super.onFinishModuleLoading();
  }
  
  @Override
  public IsLoader<MockContext> getLoader() {
    return null;
  }
  
  @Override
  public IsLoader<MockContext> getPostLoader() {
    return null;
  }
  
  @Override
  public IsCustomAlertPresenter getCustomAlertPresenter() {
    return null;
  }
  
  @Override
  public IsCustomConfirmPresenter getCustomConfirmPresenter() {
    return null;
  }
  
  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/mockShell/route01";
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
}

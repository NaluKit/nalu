package com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03;

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
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.error.ErrorComponent;
import com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.error.ErrorController;
import com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.error.IErrorComponent;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.10.1-gwt-2.8.2<< at >>2022.04.20-12:20:05<<
 */
public final class TestApplicationImpl extends AbstractApplication<MockContext> implements TestApplication {
  public TestApplicationImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadLoggerConfiguration() {
  }

  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>2.10.1-gwt-2.8.2<< used to generate this source").addMessage("=================================================================================").addMessage(""));
  }

  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell01", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell01"));
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell02", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell02"));
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell03", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell03"));
  }

  @Override
  public void loadShellFactory() {
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell01", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell01CreatorImpl(router, context, eventBus));
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell02", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell02CreatorImpl(router, context, eventBus));
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell03", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.MockShell03CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller01", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller01CreatorImpl(router, context, eventBus));
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller02", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller02CreatorImpl(router, context, eventBus));
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller03", new com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller03CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell01/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller01"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell02/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller02"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell03/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk03.content.Controller03"));
  }

  @Override
  public void loadBlockControllerFactory() {
  }

  @Override
  public void loadPopUpControllerFactory() {
  }

  @Override
  public void loadPopUpFilters() {
  }

  @Override
  public void loadErrorPopUpController() {
    ErrorController errorPopUpController = new ErrorController();
    errorPopUpController.setContext(context);
    errorPopUpController.setEventBus(eventBus);
    errorPopUpController.setRouter(router);
    IErrorComponent component = new ErrorComponent();
    component.setController(errorPopUpController);
    errorPopUpController.setComponent(component);
    component.render();
    component.bind();
    super.eventBus.addHandler(MockEvent01.TYPE, e -> errorPopUpController.onMockEvent01(e));
    errorPopUpController.bind();
    errorPopUpController.onLoad();
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
  public void loadParameterConstraintRules() {
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
    this.startRoute = "/mockShell01/route01";
  }

  @Override
  public void loadIllegalRouteTarget() {
    this.illegalRouteTarget = "";
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
  public boolean isRemoveUrlParameterAtStart() {
    return false;
  }
}

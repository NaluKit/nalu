package io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ShellFactory;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.error.ErrorComponent;
import io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.error.ErrorController;
import io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.error.IErrorComponent;
import java.lang.Override;

import java.util.Arrays;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.20-11:49:31<<
 */
public final class TestApplicationImpl extends AbstractApplication<MockContext> implements TestApplication {
  public TestApplicationImpl() {
    super();
    super.context = new io.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadLoggerConfiguration() {
  }

  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>%VERSION_TAG%<< used to generate this source").addMessage("=================================================================================").addMessage(""));
  }

  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell01", "io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.MockShell01"));
  }

  @Override
  public void loadShellFactory() {
    ShellFactory.INSTANCE.registerShell("io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.MockShell01",
                                        new io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.MockShell01CreatorImpl(router,
                                                                                                                                                               context,
                                                                                                                                                               eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.content.Controller01",
                                                  new io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.content.Controller01CreatorImpl(router,
                                                                                                                                                                                  context,
                                                                                                                                                                                  eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell01/route01", Arrays.asList(new String[]{}), "selector01", "io.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk01.content.Controller01"));
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
  public boolean isHandlingBaseHref() {
    return false;
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
  public boolean isUsingTrailingSlash() {
    return false;
  }
}

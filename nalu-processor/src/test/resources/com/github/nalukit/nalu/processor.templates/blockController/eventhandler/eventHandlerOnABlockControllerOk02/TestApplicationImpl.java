package com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02;

import com.github.nalukit.nalu.client.application.IsLoader;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.BlockControllerFactory;
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
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.16-20:35:13<<
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
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>%VERSION_TAG%<< used to generate this source").addMessage("=================================================================================").addMessage(""));
  }

  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.ui.MockShell"));
    super.shellConfiguration.getShells().add(new ShellConfig("/errorShell", "com.github.nalukit.nalu.processor.common.MockErrorShell"));
  }

  @Override
  public void loadShellFactory() {
    ShellFactory.INSTANCE.registerShell("com.github.nalukit.nalu.processor.common.ui.MockShell",
                                        new com.github.nalukit.nalu.processor.common.ui.MockShellCreatorImpl(router,
                                                                                                             context,
                                                                                                             eventBus));
    ShellFactory.INSTANCE.registerShell("com.github.nalukit.nalu.processor.common.MockErrorShell",
                                        new com.github.nalukit.nalu.processor.common.MockErrorShellCreatorImpl(router,
                                                                                                               context,
                                                                                                               eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02.content.Controller01",
                                                  new com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02.content.Controller01CreatorImpl(router,
                                                                                                                                                                                        context,
                                                                                                                                                                                        eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02.content.Controller01"));
  }

  @Override
  public void loadBlockControllerFactory() {
    BlockControllerFactory.INSTANCE.registerBlockController("blockController01",
                                                            new com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02.BlockControllerEventHandler01CreatorImpl(router,
                                                                                                                                                                                                           context,
                                                                                                                                                                                                           eventBus));
  }

  @Override
  public void loadPopUpControllerFactory() {
  }

  @Override
  public void loadPopUpFilters() {
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
    this.startRoute = "/mockShell/route01";
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

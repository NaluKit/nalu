package com.github.nalukit.nalu.processor.handler.eventhandler.twoHandler;

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
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.16-20:35:13<<
 */
public final class ApplicationWithHandlerOkImpl extends AbstractApplication<MockContext> implements ApplicationWithHandlerOk {
  public ApplicationWithHandlerOkImpl() {
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
  }

  @Override
  public void loadShellFactory() {
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.ui.MockShell", new com.github.nalukit.nalu.processor.common.ui.MockShellCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.component01.Controller01", new com.github.nalukit.nalu.processor.common.ui.component01.Controller01CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01/*", Arrays.asList(new String[]{"parameter01"}), "selector01", "com.github.nalukit.nalu.processor.common.ui.component01.Controller01"));
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
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("no ErrorPopUpController found!"));
  }

  @Override
  public void loadFilters() {
  }

  @Override
  public void loadHandlers() {
    HandlerOk01 com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01 = new HandlerOk01();
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.setContext(super.context);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.setEventBus(super.eventBus);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.setRouter(super.router);
    super.eventBus.addHandler(MockEvent01.TYPE, e -> com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.onMockEvent0101(e));
    super.eventBus.addHandler(MockEvent01.TYPE, e -> com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.onMockEvent0102(e));
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk01.bind();
    HandlerOk02 com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02 = new HandlerOk02();
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02.setContext(super.context);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02.setEventBus(super.eventBus);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02.setRouter(super.router);
    super.eventBus.addHandler(MockEvent01.TYPE, e -> com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02.onMockEvent0101(e));
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk02.bind();
    HandlerOk03 com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk03 = new HandlerOk03();
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk03.setContext(super.context);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk03.setEventBus(super.eventBus);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk03.setRouter(super.router);
    com_github_nalukit_nalu_processor_handler_eventhandler_twoHandler_HandlerOk03.bind();
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

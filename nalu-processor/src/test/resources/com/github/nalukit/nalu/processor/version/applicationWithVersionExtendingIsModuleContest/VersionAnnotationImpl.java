package com.github.nalukit.nalu.processor.version.applicationWithVersionExtendingIsModuleContest;

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
import com.github.nalukit.nalu.processor.common.MockModuleContext;
import java.lang.Override;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.4.1-gwt-2.8.2<< at >>2020.11.06-14:27:21<<
 */
public final class VersionAnnotationImpl extends AbstractApplication<MockModuleContext> implements VersionAnnotation {
  public VersionAnnotationImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockModuleContext();
    super.context.setApplicationVersion("Nalu-Test-Version");
    super.context.setApplicationBuildTime(new Timestamp(System.currentTimeMillis()));
  }

  @Override
  public void loadLoggerConfiguration() {
  }

  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>2.4.1-gwt-2.8.2<< used to generate this source").addMessage("=================================================================================").addMessage(""));
  }

  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockModuleShell"));
  }

  @Override
  public void loadShellFactory() {
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockModuleShell", new com.github.nalukit.nalu.processor.common.MockModuleShellCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.component01.ControllerModule01", new com.github.nalukit.nalu.processor.common.ui.component01.ControllerModule01CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01/*", Arrays.asList(new String[]{"parameter01"}), "selector01", "com.github.nalukit.nalu.processor.common.ui.component01.ControllerModule01"));
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
  }

  @Override
  public void loadCompositeReferences() {
  }

  @Override
  public void loadModules() {
    super.onFinishModuleLoading();
  }

  @Override
  public IsLoader<MockModuleContext> getApplicationLoader() {
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

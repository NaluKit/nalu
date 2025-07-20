package io.github.nalukit.nalu.processor.common.application.applicationWithComposite01;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.internal.CompositeReference;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
import io.github.nalukit.nalu.client.internal.application.CompositeConditionFactory;
import io.github.nalukit.nalu.client.internal.application.CompositeFactory;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ShellFactory;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2020.11.18-06:55:01<<
 */
public final class ApplicationWithComposite01Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite01 {
  public ApplicationWithComposite01Impl() {
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
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "io.github.nalukit.nalu.processor.common.ui.MockShell"));
  }
  
  @Override
  public void loadShellFactory() {
    ShellFactory.INSTANCE.registerShell("io.github.nalukit.nalu.processor.common.ui.MockShell",
                                        new io.github.nalukit.nalu.processor.common.ui.MockShellCreatorImpl(router,
                                                                                                             context,
                                                                                                             eventBus));
  }
  
  @Override
  public void loadCompositeController() {
    CompositeFactory.INSTANCE.registerComposite("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.composite.CompositeController01",
                                                new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.composite.CompositeController01CreatorImpl(router,
                                                                                                                                                                     context,
                                                                                                                                                                     eventBus));
  }
  
  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.ControllerWithComposite01",
                                                  new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.ControllerWithComposite01CreatorImpl(router,
                                                                                                                                                                 context,
                                                                                                                                                                 eventBus));
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.ControllerWithComposite01",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.composite.CompositeController01",
                                                         super.alwaysLoadComposite);
  }
  
  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01/*", Arrays.asList(new String[]{"parameter01"}), "selector01", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.ControllerWithComposite01"));
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
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.ControllerWithComposite01", "testComposite", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.composite.CompositeController01", "selector", false));
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
}

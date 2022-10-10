package com.github.nalukit.nalu.processor.common.application.applicationWithComposite07;

import com.github.nalukit.nalu.client.application.IsLoader;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.internal.CompositeReference;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.CompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.CompositeCondition01;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.CompositeCondition02;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2021.07.28-16:59:17<<
 */
public final class ApplicationWithComposite07Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite07 {
  public ApplicationWithComposite07Impl() {
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
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03CreatorImpl(router, context, eventBus));
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04CreatorImpl(router, context, eventBus));
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadComponents() {
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05CreatorImpl(router, context, eventBus));
    CompositeCondition01 compositeCondition01_1 = new CompositeCondition01();
    compositeCondition01_1.setContext(super.context);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", compositeCondition01_1);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", compositeCondition01_1);
    CompositeCondition02 compositeCondition02_1 = new CompositeCondition02();
    compositeCondition02_1.setContext(super.context);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05", compositeCondition02_1);
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06CreatorImpl(router, context, eventBus));
    CompositeCondition01 compositeCondition01_2 = new CompositeCondition01();
    compositeCondition01_2.setContext(super.context);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", compositeCondition01_2);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", compositeCondition01_2);
    CompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", super.alwaysLoadComposite);
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route05/*", Arrays.asList(new String[]{"parameter05"}), "selector05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route06/*", Arrays.asList(new String[]{"parameter06"}), "selector06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06"));
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
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite01", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite02", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", "selector", false));
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05", "selector", false));
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite01", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite02", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", "selector", false));
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
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
    this.startRoute = "/mockShell/route05";
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

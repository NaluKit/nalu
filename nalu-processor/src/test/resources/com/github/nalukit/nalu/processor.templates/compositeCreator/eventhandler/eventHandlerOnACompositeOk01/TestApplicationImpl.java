package com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01;

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
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version 2.12.5 at 2023.01.30-21:40:27
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
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>2.12.5<< used to generate this source").addMessage("=================================================================================").addMessage(""));
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
    ShellFactory.INSTANCE.registerShell("com.github.nalukit.nalu.processor.common.ui.MockShell", new com.github.nalukit.nalu.processor.common.ui.MockShellCreatorImpl(router, context, eventBus));
    ShellFactory.INSTANCE.registerShell("com.github.nalukit.nalu.processor.common.MockErrorShell", new com.github.nalukit.nalu.processor.common.MockErrorShellCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
    CompositeFactory.INSTANCE.registerComposite("com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.composite.Composite01", new com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.composite.Composite01CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.Controller01", new com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.Controller01CreatorImpl(router, context, eventBus));
    CompositeConditionFactory.INSTANCE.registerCondition("com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.Controller01", "com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.composite.Composite01", super.alwaysLoadComposite);
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.Controller01"));
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
    this.compositeReferences.add(new CompositeReference("com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.Controller01", "composite01", "com.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk01.content.composite.Composite01", "composite01", false));
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

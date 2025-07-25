package io.github.nalukit.nalu.processor.common.application.applicationWithComposite03;

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
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.CompositeCondition03;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2020.11.18-06:55:58<<
 */
public final class ApplicationWithComposite03Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite03 {
  public ApplicationWithComposite03Impl() {
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
    CompositeFactory.INSTANCE.registerComposite("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03",
                                                new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03CreatorImpl(router,
                                                                                                                                                                     context,
                                                                                                                                                                     eventBus));
  }
  
  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03",
                                                  new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03CreatorImpl(router,
                                                                                                                                                                 context,
                                                                                                                                                                 eventBus));
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController01",
                                                         super.alwaysLoadComposite);
    CompositeCondition03 compositeCondition03_1 = new CompositeCondition03();
    compositeCondition03_1.setContext(super.context);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03",
                                                         compositeCondition03_1);
  }
  
  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route03/*", Arrays.asList(new String[]{"parameter03"}), "selector03", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03"));
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
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "testComposite01", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController01", "selector", false));
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "testComposite03", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03", "selector", false));
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
    this.startRoute = "/mockShell/route03";
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

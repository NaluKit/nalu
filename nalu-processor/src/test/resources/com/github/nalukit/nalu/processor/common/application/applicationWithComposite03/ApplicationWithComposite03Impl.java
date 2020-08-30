package com.github.nalukit.nalu.processor.common.application.applicationWithComposite03;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerCompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.CompositeCondition03;
import java.lang.Override;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2020.08.30-17:52:33<<
 */
public final class ApplicationWithComposite03Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite03 {
  public ApplicationWithComposite03Impl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }
  
  @Override
  public void loadLoggerConfiguration() {
  }
  
  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create()                          .sdmOnly(true)                          .addMessage("=================================================================================")                          .addMessage("Nalu processor version  >>HEAD-SNAPSHOT<< used to generate this source")                          .addMessage("=================================================================================")                          .addMessage(""));
    ClientLogger.get().logDetailed("", 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    StringBuilder sb01 = new StringBuilder();
    sb01.append("Nalu processor version  >>HEAD-SNAPSHOT<< used to generate this source");
    ClientLogger.get().logDetailed(sb01.toString(), 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    ClientLogger.get().logDetailed("", 0);
  }
  
  @Override
  public void loadDebugConfiguration() {
  }
  
  @Override
  public IsTracker loadTrackerConfiguration() {
    return null;
  }
  
  @Override
  public void loadShells() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load shell references");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockShell"));
    sb01.setLength(0);
    sb01.append("register shell >>/mockShell<< with class >>com.github.nalukit.nalu.processor.common.MockShell<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }
  
  @Override
  public void loadShellFactory() {
    // create ShellCreator for: com.github.nalukit.nalu.processor.common.MockShell
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockShell", new com.github.nalukit.nalu.processor.common.MockShellCreatorImpl(router, context, eventBus));
  }
  
  @Override
  public void loadCompositeController() {
    // create Composite for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03CreatorImpl(router, context, eventBus));
  }
  
  @Override
  public void loadComponents() {
    // create ControllerCreator for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03CreatorImpl(router, context, eventBus));
    // register conditions of composites for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController01", super.alwaysLoadComposite);
    CompositeCondition03 compositeCondition03_1 = new CompositeCondition03();
    compositeCondition03_1.setContext(super.context);
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03", compositeCondition03_1);
  }
  
  @Override
  public void loadRoutes() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load routes");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route03/*", Arrays.asList(new String[]{"parameter03"}), "selector03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route03/*<< with parameter >>parameter03<< for selector >>selector03<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }
  
  @Override
  public void loadBlockControllerFactory() {
  }
  
  @Override
  public void loadPopUpControllerFactory() {
  }
  
  @Override
  public void loadErrorPopUpController() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("no ErrorPopUpController found!°");
  }
  
  @Override
  public void loadFilters() {
  }
  
  @Override
  public void loadHandlers() {
  }
  
  @Override
  public void loadCompositeReferences() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load composite references");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "testComposite01", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController01", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite01<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03", "testComposite03", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite03<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }
  
  @Override
  public void loadModules() {
  }
  
  @Override
  public IsApplicationLoader<MockContext> getApplicationLoader() {
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
    StringBuilder sb01 = new StringBuilder();
    this.startRoute = "/mockShell/route03";
    sb01.append("found startRoute >>/mockShell/route03<<");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    sb01.setLength(0);
    ClientLogger.get().logDetailed(sb01.toString(), 2);
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

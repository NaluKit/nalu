package com.github.nalukit.nalu.processor.common.application.applicationWithComposite06;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
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
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.CompositeCondition01;
import java.lang.Override;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.0.1<< at >>2020.11.07-07:25:37<< */
public final class ApplicationWithComposite06Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite06 {
  public ApplicationWithComposite06Impl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void logProcessorVersion() {
    ClientLogger.get().logDetailed("", 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    StringBuilder sb01 = new StringBuilder();
    sb01.append("Nalu processor version  >>2.0.1<< used to generate this source");
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
    // create Composite for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03CreatorImpl(router, context, eventBus));
    // create Composite for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04
    CompositeFactory.get().registerComposite("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadComponents() {
    // create ControllerCreator for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05CreatorImpl(router, context, eventBus));
    // register conditions of composites for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05
    CompositeCondition01 compositeCondition01_1 = new CompositeCondition01();
    compositeCondition01_1.setContext(super.context);
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03", compositeCondition01_1);
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04", compositeCondition01_1);
    // create ControllerCreator for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06", new com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06CreatorImpl(router, context, eventBus));
    // register conditions of composites for: com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06
    CompositeCondition01 compositeCondition01_2 = new CompositeCondition01();
    compositeCondition01_2.setContext(super.context);
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03", compositeCondition01_2);
    ControllerCompositeConditionFactory.get().registerCondition("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04", compositeCondition01_2);
  }

  @Override
  public void loadRoutes() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load routes");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route05/*", Arrays.asList(new String[]{"parameter05"}), "selector05", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route05/*<< with parameter >>parameter05<< for selector >>selector05<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route06/*", Arrays.asList(new String[]{"parameter06"}), "selector06", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route06/*<< with parameter >>parameter06<< for selector >>selector06<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06<<");
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
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05", "testComposite01", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite01<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05", "testComposite02", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite02<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite05<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06", "testComposite01", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite01<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeControllerReferences.add(new CompositeControllerReference("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06", "testComposite02", "com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite02<< for controller >>com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.ControllerWithComposite06<< in selector >>selector<<");
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
    this.startRoute = "/mockShell/route06";
    sb01.append("found startRoute >>/mockShell/route06<<");
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

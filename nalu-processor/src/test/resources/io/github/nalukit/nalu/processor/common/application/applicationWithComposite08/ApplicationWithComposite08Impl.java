package io.github.nalukit.nalu.processor.common.application.applicationWithComposite07;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.internal.ClientLogger;
import io.github.nalukit.nalu.client.internal.CompositeReference;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
import io.github.nalukit.nalu.client.internal.application.CompositeConditionFactory;
import io.github.nalukit.nalu.client.internal.application.CompositeFactory;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ShellFactory;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.CompositeCondition01;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.CompositeCondition02;
import java.lang.Override;
import java.util.Arrays;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2020.10.23-11:44:39<< */
public final class ApplicationWithComposite07Impl extends AbstractApplication<MockContext> implements ApplicationWithComposite07 {
  public ApplicationWithComposite07Impl() {
    super();
    super.context = new io.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void logProcessorVersion() {
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
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "io.github.nalukit.nalu.processor.common.ui.MockShell"));
    sb01.setLength(0);
    sb01.append("register shell >>/mockShell<< with class >>io.github.nalukit.nalu.processor.common.ui.MockShell<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }

  @Override
  public void loadShellFactory() {
    // create ShellCreator for: io.github.nalukit.nalu.processor.common.ui.MockShell
    ShellFactory.INSTANCE.registerShell("io.github.nalukit.nalu.processor.common.ui.MockShell",
                                        new io.github.nalukit.nalu.processor.common.ui.MockShellCreatorImpl(router,
                                                                                                             context,
                                                                                                             eventBus));
  }

  @Override
  public void loadCompositeController() {
    // create Composite for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03
    CompositeFactory.INSTANCE.registerComposite("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03",
                                                new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03CreatorImpl(router,
                                                                                                                                                                     context,
                                                                                                                                                                     eventBus));
    // create Composite for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04
    CompositeFactory.INSTANCE.registerComposite("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04",
                                                new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04CreatorImpl(router,
                                                                                                                                                                     context,
                                                                                                                                                                     eventBus));
    // create Composite for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05
    CompositeFactory.INSTANCE.registerComposite("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05",
                                                new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05CreatorImpl(router,
                                                                                                                                                                     context,
                                                                                                                                                                     eventBus));
  }

  @Override
  public void loadComponents() {
    // create ControllerCreator for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05",
                                                  new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05CreatorImpl(router,
                                                                                                                                                                 context,
                                                                                                                                                                 eventBus));
    // register conditions of composites for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05
    CompositeCondition01 compositeCondition01_1 = new CompositeCondition01();
    compositeCondition01_1.setContext(super.context);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03",
                                                         compositeCondition01_1);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04",
                                                         compositeCondition01_1);
    CompositeCondition02 compositeCondition02_1 = new CompositeCondition02();
    compositeCondition02_1.setContext(super.context);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05",
                                                         compositeCondition02_1);
    // create ControllerCreator for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06",
                                                  new io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06CreatorImpl(router,
                                                                                                                                                                 context,
                                                                                                                                                                 eventBus));
    // register conditions of composites for: io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06
    CompositeCondition01 compositeCondition01_2 = new CompositeCondition01();
    compositeCondition01_2.setContext(super.context);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03",
                                                         compositeCondition01_2);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04",
                                                         compositeCondition01_2);
    CompositeConditionFactory.INSTANCE.registerCondition("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06",
                                                         "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03",
                                                         super.alwaysLoadComposite);
  }

  @Override
  public void loadRoutes() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load routes");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route05/*", Arrays.asList(new String[]{"parameter05"}), "selector05", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route05/*<< with parameter >>parameter05<< for selector >>selector05<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route06/*", Arrays.asList(new String[]{"parameter06"}), "selector06", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route06/*<< with parameter >>parameter06<< for selector >>selector06<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
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
    StringBuilder sb01 = new StringBuilder();
    sb01.append("no ErrorPopUpController found!");
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
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite01", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite01<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite02", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite02<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05", "testComposite03", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite03<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite05<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite01", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite01<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite02", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite02<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
    this.compositeReferences.add(new CompositeReference("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06", "testComposite03", "io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03", "selector", false));
    sb01.setLength(0);
    sb01.append("register composite >>testComposite03<< for controller >>io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.ControllerWithComposite06<< in selector >>selector<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }

  @Override
  public void loadParameterConstraintRules() {
  }

  @Override
  public void loadModules() {
  }

  @Override
  public IsLoader<MockContext> getApplicationLoader() {
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    StringBuilder sb01 = new StringBuilder();
    this.startRoute = "/mockShell/route05";
    sb01.append("found startRoute >>/mockShell/route05<<");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    sb01.setLength(0);
    ClientLogger.get().logDetailed(sb01.toString(), 2);
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
  public boolean isStayOnSide() {
    return false;
  }

  @Override
  public boolean isRemoveUrlParameterAtStart() {
    return false;
  }
}

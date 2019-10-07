package com.github.nalukit.nalu.processor.errorPopUpController.errorPopUpAnnotationOk;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.errorPopUp01.ErrorEventComponent01;
import com.github.nalukit.nalu.processor.common.ui.errorPopUp01.ErrorEventController01;
import com.github.nalukit.nalu.processor.common.ui.errorPopUp01.IErrorEventComponent01;
import java.lang.Override;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.0.0-rc-3<< at >>2019.09.27-18:17:30<< */
public final class ErrorPopUpAnnotationOkImpl extends AbstractApplication<MockContext> implements ErrorPopUpAnnotationOk {
  public ErrorPopUpAnnotationOkImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void logProcessorVersion() {
    ClientLogger.get().logDetailed("", 0);
    ClientLogger.get().logDetailed("=================================================================================", 0);
    StringBuilder sb01 = new StringBuilder();
    sb01.append("Nalu processor version  >>2.0.0-rc-3<< used to generate this source");
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
    super.shellConfiguration.getShells().add(new ShellConfig("/errorShell", "com.github.nalukit.nalu.processor.common.MockErrorShell"));
    sb01.setLength(0);
    sb01.append("register shell >>/errorShell<< with class >>com.github.nalukit.nalu.processor.common.MockErrorShell<<");
    ClientLogger.get().logDetailed(sb01.toString(), 3);
  }

  @Override
  public void loadShellFactory() {
    // create ShellCreator for: com.github.nalukit.nalu.processor.common.MockShell
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockShell", new com.github.nalukit.nalu.processor.common.MockShellCreatorImpl(router, context, eventBus));
    // create ShellCreator for: com.github.nalukit.nalu.processor.common.MockErrorShell
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockErrorShell", new com.github.nalukit.nalu.processor.common.MockErrorShellCreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    // create ControllerCreator for: com.github.nalukit.nalu.processor.common.ui.component01.Controller01
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.component01.Controller01", new com.github.nalukit.nalu.processor.common.ui.component01.Controller01CreatorImpl(router, context, eventBus));
  }

  @Override
  public void loadRoutes() {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("load routes");
    ClientLogger.get().logDetailed(sb01.toString(), 2);
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01/*", Arrays.asList(new String[]{"parameter01"}), "selector01", "com.github.nalukit.nalu.processor.common.ui.component01.Controller01"));
    sb01.setLength(0);
    sb01.append("register route >>/mockShell/route01/*<< with parameter >>parameter01<< for selector >>selector01<< for controller >>com.github.nalukit.nalu.processor.common.ui.component01.Controller01<<");
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
    sb01.append("ErrorPopUpController found!");
    sb01.append("create ErrorPopUpController >>com.github.nalukit.nalu.processor.common.ui.errorPopUp01.ErrorEventController01<<");
    ErrorEventController01 errorPopUpController = new ErrorEventController01();
    errorPopUpController.setContext(context);
    errorPopUpController.setEventBus(eventBus);
    errorPopUpController.setRouter(router);
    sb01.setLength(0);
    sb01.append("controller >>").append(errorPopUpController.getClass().getCanonicalName()).append("<< --> created and data injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    IErrorEventComponent01 component = new ErrorEventComponent01();
    sb01.setLength(0);
    sb01.append("component >>com.github.nalukit.nalu.processor.common.ui.errorPopUp01.ErrorEventComponent01<< --> created using new");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.setController(errorPopUpController);
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    errorPopUpController.setComponent(component);
    sb01.setLength(0);
    sb01.append("controller >>").append(errorPopUpController.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.render();
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.bind();
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    ClientLogger.get().logSimple("controller >>com.github.nalukit.nalu.processor.common.ui.errorPopUp01.ErrorEventController01<< created", 3);
    errorPopUpController.onLoad();
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
  }

  @Override
  public void loadModules() {
  }

  @Override
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    StringBuilder sb01 = new StringBuilder();
    this.startRoute = "/mockShell/route01";
    sb01.append("found startRoute >>/mockShell/route01<<");
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

  @Override
  public PropertyFactory.ErrorHandlingMethod getErrorHandlingMethod() {
    return PropertyFactory.ErrorHandlingMethod.EVENT;
  }
}
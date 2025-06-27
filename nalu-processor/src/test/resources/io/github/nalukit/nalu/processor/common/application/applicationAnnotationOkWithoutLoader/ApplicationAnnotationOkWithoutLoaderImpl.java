package io.github.nalukit.nalu.processor.common.application.applicationAnnotationOkWithoutLoader;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
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
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2020.11.18-06:52:32<<
 */
public final class ApplicationAnnotationOkWithoutLoaderImpl extends AbstractApplication<MockContext> implements ApplicationAnnotationOkWithoutLoader {
  public ApplicationAnnotationOkWithoutLoaderImpl() {
    super();
    super.context = new io.github.nalukit.nalu.processor.common.MockContext();
  }
  
  @Override
  public void loadLoggerConfiguration() {
  }
  
  @Override
  public void logProcessorVersion() {
    this.eventBus.fireEvent(LogEvent.create().sdmOnly(true).addMessage("=================================================================================").addMessage("Nalu processor version  >>HEAD-SNAPSHOT<< used to generate this source").addMessage("=================================================================================").addMessage(""));
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
  }
  
  @Override
  public void loadComponents() {
    ControllerFactory.INSTANCE.registerController("io.github.nalukit.nalu.processor.common.ui.component01.Controller01",
                                                  new io.github.nalukit.nalu.processor.common.ui.component01.Controller01CreatorImpl(router,
                                                                                                                                      context,
                                                                                                                                      eventBus));
  }
  
  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01/*", Arrays.asList(new String[]{"parameter01"}), "selector01", "io.github.nalukit.nalu.processor.common.ui.component01.Controller01"));
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

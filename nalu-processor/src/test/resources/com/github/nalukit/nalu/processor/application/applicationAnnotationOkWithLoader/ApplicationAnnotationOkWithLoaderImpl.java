package com.github.nalukit.nalu.processor.application.applicationAnnotationOkWithLoader;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.*;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;
import com.github.nalukit.nalu.processor.common.ui.component01.Component01;
import com.github.nalukit.nalu.processor.common.ui.component01.Controller01;
import com.github.nalukit.nalu.processor.common.ui.component01.IComponent01;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;

public final class ApplicationAnnotationOkWithLoaderImpl extends AbstractApplication<MockContext> implements ApplicationAnnotationOkWithLoader {
  public ApplicationAnnotationOkWithLoaderImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadDebugConfiguration() {
  }

  @Override
  public void loadShells() {
    super.shellConfiguration.getShells().add(new ShellConfig("/mockShell", "com.github.nalukit.nalu.processor.common.MockShell"));
  }

  @Override
  public void loadShellFactory() {
    // create ShellCreator for: com.github.nalukit.nalu.processor.common.MockShell
    ShellFactory.get().registerShell("com.github.nalukit.nalu.processor.common.MockShell", new IsShellCreator() {
      @Override
      public ShellInstance create() {
        StringBuilder sb01 = new StringBuilder();
        ShellInstance shellInstance = new ShellInstance();
        shellInstance.setShellClassName("com.github.nalukit.nalu.processor.common.MockShell");
        sb01.append("shell >>com.github.nalukit.nalu.processor.common.MockShell<< --> will be created");
        ClientLogger.get().logSimple(sb01.toString(), 1);
        MockShell shell = new MockShell();
        shell.setContext(context);
        shell.setEventBus(eventBus);
        shell.setRouter(router);
        sb01 = new StringBuilder();
        sb01.append("shell >>").append(shell.getClass().getCanonicalName()).append("<< --> created and data injected");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        sb01 = new StringBuilder();
        sb01.append("shell >>").append(shell.getClass().getCanonicalName()).append("<< --> call bind()-method");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        shell.bind();
        sb01.append("shell >>").append(shell.getClass().getCanonicalName()).append("<< --> called bind()-method");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        shellInstance.setShell(shell);
        return shellInstance;
      }
    });
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    // create ControllerCreator for: com.github.nalukit.nalu.processor.common.ui.component01.Controller01
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.common.ui.component01.Controller01", new IsControllerCreator() {
      @Override
      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
        StringBuilder sb01 = new StringBuilder();
        ControllerInstance controllerInstance = new ControllerInstance();
        controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.common.ui.component01.Controller01");
        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.common.ui.component01.Controller01");
        if (storedController == null) {
          sb01.append("controller >>com.github.nalukit.nalu.processor.common.ui.component01.Controller01<< --> will be created");
          ClientLogger.get().logSimple(sb01.toString(), 3);
          Controller01 controller = new Controller01();
          controllerInstance.setController(controller);
          controllerInstance.setChached(false);
          controller.setContext(context);
          controller.setEventBus(eventBus);
          controller.setRouter(router);
          controller.setRestored(false);
          sb01 = new StringBuilder();
          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          IComponent01 component = new Component01();
          sb01 = new StringBuilder();
          sb01.append("component >>com.github.nalukit.nalu.processor.common.ui.component01.Component01<< --> created using new");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          component.setController(controller);
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          controller.setComponent(component);
          sb01 = new StringBuilder();
          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          component.render();
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          component.bind();
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          ClientLogger.get().logSimple("controller >>com.github.nalukit.nalu.processor.common.ui.component01.Component01<< created for route >>/mockShell/route01<<", 3);
        } else {
          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          controllerInstance.setController(storedController);
          controllerInstance.setChached(true);
          controllerInstance.getController().setRestored(true);
        }
        return controllerInstance;
      }
    });
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/mockShell/route01", Arrays.asList(new String[]{"parameter01"}), "selector01", "com.github.nalukit.nalu.processor.common.ui.component01.Controller01"));
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
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return new MockApplicationLoader();
  }

  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/mockShell/route01";
    this.errorRoute = "/mockShell/route01";
  }
}

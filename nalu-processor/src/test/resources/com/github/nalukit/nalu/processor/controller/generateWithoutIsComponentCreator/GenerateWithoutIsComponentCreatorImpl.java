package com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.ControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;
import com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Component;
import com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller;
import com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.IContent01Component;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;

public final class GenerateWithoutIsComponentCreatorImpl extends AbstractApplication<MockContext> implements GenerateWithoutIsComponentCreator {
  public GenerateWithoutIsComponentCreatorImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadDebugConfiguration() {
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    // shell ...
    MockShell shell = new MockShell();
    shell.setRouter(this.router);
    shell.setEventBus(this.eventBus);
    shell.setContext(this.context);
    super.shell = shell;
    super.router.setShell(this.shell);
    shell.bind();
    ClientLogger.get().logDetailed("AbstractApplicationImpl: shell created", 1);
    // create ControllerCreator for: com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller
    ControllerFactory.get().registerController("com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller", new ControllerCreator() {
      @Override
      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
        StringBuilder sb01 = new StringBuilder();
        ControllerInstance controllerInstance = new ControllerInstance();
        controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller");
        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller");
        if (storedController == null) {
          sb01.append("controller >>com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller<< --> will be created");
          ClientLogger.get().logSimple(sb01.toString(), 1);
          Content01Controller controller = new Content01Controller();
          controllerInstance.setController(controller);
          controllerInstance.setChached(false);
          controller.setContext(context);
          controller.setEventBus(eventBus);
          controller.setRouter(router);
          controller.setRestored(false);
          sb01 = new StringBuilder();
          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          IContent01Component component = new Content01Component();
          sb01 = new StringBuilder();
          sb01.append("component >>com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Component<< --> created using new");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          component.setController(controller);
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          controller.setComponent(component);
          sb01 = new StringBuilder();
          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          component.render();
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          component.bind();
          sb01 = new StringBuilder();
          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          ClientLogger.get().logSimple("controller >>com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Component<< created for route >>/route01<<", 1);
        } else {
          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
          ClientLogger.get().logDetailed(sb01.toString(), 2);
          controllerInstance.setController(storedController);
          controllerInstance.setChached(true);
        }
        return controllerInstance;
      }
    });
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/route01", Arrays.asList(new String[]{}), "selector01", "com.github.nalukit.nalu.processor.controller.generateWithoutIsComponentCreator.ui.content01.Content01Controller"));
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
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/route01";
    this.errorRoute = "WhenShallWeThreeMeetAgainInThunderLightningOrInRain";
  }
}

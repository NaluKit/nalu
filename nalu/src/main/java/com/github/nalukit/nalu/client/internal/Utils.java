package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;

public class Utils {

  private static Utils instance;

  private Utils() {
  }

  public static Utils get() {
    if (instance == null) {
      instance = new Utils();
    }
    return instance;
  }

  public void deactivateController(AbstractComponentController<?, ?, ?> controller,
                                   boolean handlingModeReuse) {
    // deactivate controller
    controller.deactivate();
    // remove handlers
    controller.removeHandlers();
    if (!handlingModeReuse) {
      controller.onDetach();
      controller.getComponent()
                .onDetach();
    }
  }

  public void stopController(AbstractComponentController<?, ?, ?> controller) {
    controller.onDetach();
    // stop controller
    controller.stop();
    // remove global handlers
    controller.removeGlobalHandlers();
    controller.onDetach();
    controller.getComponent()
              .onDetach();
    // remove handlers on component elements
    controller.getComponent()
              .removeHandlers();
  }

  public void deactivateCompositeController(AbstractCompositeController<?, ?, ?> compositeController) {
    compositeController.deactivate();
    // remove handlers
    compositeController.removeHandlers();
  }

  public void stopCompositeController(AbstractCompositeController<?, ?, ?> compositeController) {
    if (!compositeController.isCached()) {
      compositeController.stop();
      // remove global handlers
      compositeController.removeGlobalHandlers();
    }
    compositeController.onDetach();
    compositeController.getComponent()
                       .onDetach();
    // remove handlers on component elements
    compositeController.getComponent()
                       .removeHandlers();
  }

}

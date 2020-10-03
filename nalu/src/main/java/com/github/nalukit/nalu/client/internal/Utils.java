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
    controller.onDetach();
    controller.getComponent()
              .onDetach();
    controller.getComponent()
              .removeHandlers();
  }
  
  public void deactivateCompositeController(AbstractCompositeController<?, ?, ?> compositeController) {
    compositeController.deactivate();
    compositeController.removeHandlers();
  }
  
  public void stopCompositeController(AbstractCompositeController<?, ?, ?> compositeController) {
    if (!compositeController.isCached()) {
      compositeController.stop();
    }
    compositeController.onDetach();
    compositeController.getComponent()
                       .onDetach();
    compositeController.getComponent()
                       .removeHandlers();
  }
  
}

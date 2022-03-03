package com.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.nalukit.nalu.client.component.IsComponent;

public interface IBadComponent
    extends IsComponent<IBadComponent.Controller, String> {

  interface Controller
      extends IsComponent.Controller {

  }
}

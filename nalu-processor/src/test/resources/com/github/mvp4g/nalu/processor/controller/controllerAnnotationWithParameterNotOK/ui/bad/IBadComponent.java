package com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.mvp4g.nalu.client.component.IsComponent;

public interface IBadComponent
    extends IsComponent<IBadComponent.Controller, String> {

  interface Controller
      extends IsComponent.Controller {

  }
}

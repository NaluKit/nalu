package com.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/mockShell/badRoute/:variable01",
            selector = "selector01",
            component = BadComponent.class,
            componentInterface = IBadComponent.class)
public class BadController
    extends AbstractComponentController<MockContext, IBadComponent, String>
    implements IBadComponent.Controller {

  public BadController() {
  }
}

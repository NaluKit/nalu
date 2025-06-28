package io.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;

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

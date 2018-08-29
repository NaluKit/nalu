package com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.github.mvp4g.nalu.processor.common.MockContext;

@Controller(route = "/badRoute/:variable01",
  selector = "selector01",
  component = BadComponent.class,
  componentInterface = IBadComponent.class)
public class BadController
  extends AbstractComponentController<MockContext, IBadComponent, String>
  implements IBadComponent.Controller {

  public BadController() {
  }
}

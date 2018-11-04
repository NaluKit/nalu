package com.github.nalukit.nalu.processor.common.ui.component04;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/[mockShell|mockShell02]/route04/:variable01",
            selector = "selector04",
            component = Component04.class,
            componentInterface = IComponent04.class)
public class Controller04
    extends AbstractComponentController<MockContext, IComponent04, String>
    implements IComponent04.Controller {

  public Controller04() {
  }

  public void setVariable01(String variable01) {
  }
}

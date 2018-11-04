package com.github.nalukit.nalu.processor.common.ui.component03;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/[mockShell|mockShell02]/route03/:variable01",
            selector = "selector03",
            component = Component03.class,
            componentInterface = IComponent03.class)
public class Controller03
    extends AbstractComponentController<MockContext, IComponent03, String>
    implements IComponent03.Controller {

  public Controller03() {
  }

  public void setVariable01(String variable01) {
  }
}

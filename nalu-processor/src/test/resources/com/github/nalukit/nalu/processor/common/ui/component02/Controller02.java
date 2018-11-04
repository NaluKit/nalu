package com.github.nalukit.nalu.processor.common.ui.component02;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/mockShell/route02/:variable01",
            selector = "selector02",
            component = Component02.class,
            componentInterface = IComponent02.class)
public class Controller02
    extends AbstractComponentController<MockContext, IComponent02, String>
    implements IComponent02.Controller {

  public Controller02() {
  }

  public void setVariable01(String variable01) {
  }
}

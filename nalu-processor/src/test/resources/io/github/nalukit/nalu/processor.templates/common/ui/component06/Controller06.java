package io.github.nalukit.nalu.processor.common.ui.component06;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/mockShell",
            selector = "selector06",
            component = Component06.class,
            componentInterface = IComponent06.class)
public class Controller06
    extends AbstractComponentController<MockContext, IComponent06, String>
    implements IComponent06.Controller {
  
  public Controller06() {
  }
  
}

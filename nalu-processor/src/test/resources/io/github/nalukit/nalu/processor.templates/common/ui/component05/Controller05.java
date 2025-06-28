package io.github.nalukit.nalu.processor.common.ui.component05;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/*/route05/:variable01",
            selector = "selector05",
            component = Component05.class,
            componentInterface = IComponent05.class)
public class Controller05
    extends AbstractComponentController<MockContext, IComponent05, String>
    implements IComponent05.Controller {
  
  public Controller05() {
  }
  
  public void setVariable01(String variable01) {
  }
  
}

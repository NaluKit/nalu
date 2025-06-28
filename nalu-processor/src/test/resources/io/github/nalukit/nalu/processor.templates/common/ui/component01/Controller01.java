package io.github.nalukit.nalu.processor.common.ui.component01;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/mockShell/route01/:parameter01",
            selector = "selector01",
            component = Component01.class,
            componentInterface = IComponent01.class)
public class Controller01
    extends AbstractComponentController<MockContext, IComponent01, String>
    implements IComponent01.Controller {
  
  public Controller01() {
  }
  
}

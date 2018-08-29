package com.github.mvp4g.nalu.processor.common.ui.component01;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.github.mvp4g.nalu.processor.common.MockContext;

@Controller(route = "/reoute01",
  selector = "selector01",
  component = Component01.class,
  componentInterface = IComponent01.class)
public class Controller01
  extends AbstractComponentController<MockContext, IComponent01, String>
  implements IComponent01.Controller {

  public Controller01() {
  }
}

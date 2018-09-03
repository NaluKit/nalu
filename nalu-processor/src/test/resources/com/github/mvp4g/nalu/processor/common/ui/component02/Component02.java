package com.github.mvp4g.nalu.processor.common.ui.component02;

import com.github.mvp4g.nalu.client.component.AbstractComponent;

public class Component02
  extends AbstractComponent<IComponent02.Controller, String>
  implements IComponent02 {

  public Component02() {
  }

  @Override
  public void render() {
    initElement("Component02");
  }
}

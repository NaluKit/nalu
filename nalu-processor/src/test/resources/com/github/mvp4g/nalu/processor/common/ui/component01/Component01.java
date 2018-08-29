package com.github.mvp4g.nalu.processor.common.ui.component01;

import com.github.mvp4g.nalu.client.component.AbstractComponent;

public class Component01
  extends AbstractComponent<IComponent01.Controller, String>
  implements IComponent01 {

  public Component01() {
  }

  @Override
  public String render() {
    return "Component01";
  }
}

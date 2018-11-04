package com.github.nalukit.nalu.processor.common.ui.component01;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class Component01
    extends AbstractComponent<IComponent01.Controller, String>
    implements IComponent01 {

  public Component01() {
  }

  @Override
  public void render() {
    initElement("Component01");
  }
}

package com.github.nalukit.nalu.processor.common.ui.component03;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class Component03
    extends AbstractComponent<IComponent03.Controller, String>
    implements IComponent03 {

  public Component03() {
  }

  @Override
  public void render() {
    initElement("Component03");
  }
}

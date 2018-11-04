package com.github.nalukit.nalu.processor.common.ui.component04;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class Component04
    extends AbstractComponent<IComponent04.Controller, String>
    implements IComponent04 {

  public Component04() {
  }

  @Override
  public void render() {
    initElement("Component04");
  }
}

package com.github.nalukit.nalu.processor.common.ui.component02;

import com.github.nalukit.nalu.client.component.AbstractComponent;

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

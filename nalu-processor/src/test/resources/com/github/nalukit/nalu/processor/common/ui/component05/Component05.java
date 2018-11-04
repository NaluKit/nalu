package com.github.nalukit.nalu.processor.common.ui.component05;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class Component05
    extends AbstractComponent<IComponent05.Controller, String>
    implements IComponent05 {

  public Component05() {
  }

  @Override
  public void render() {
    initElement("Component05");
  }
}

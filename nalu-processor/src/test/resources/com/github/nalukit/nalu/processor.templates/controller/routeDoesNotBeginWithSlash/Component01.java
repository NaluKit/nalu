package com.github.nalukit.nalu.processor.controller.routeDoesNotBeginWithSlash;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

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

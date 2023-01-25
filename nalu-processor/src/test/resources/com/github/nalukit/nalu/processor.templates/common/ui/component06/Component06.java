package com.github.nalukit.nalu.processor.common.ui.component06;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

public class Component06
    extends AbstractComponent<IComponent06.Controller, String>
    implements IComponent06 {
  
  public Component06() {
  }
  
  @Override
  public void render() {
    initElement("Component06");
  }
  
}

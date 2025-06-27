package io.github.nalukit.nalu.processor.common.ui.component04;

import io.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

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

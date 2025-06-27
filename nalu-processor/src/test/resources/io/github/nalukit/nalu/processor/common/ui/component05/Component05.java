package io.github.nalukit.nalu.processor.common.ui.component05;

import io.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

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

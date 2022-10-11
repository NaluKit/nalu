package com.github.nalukit.nalu.processor.shell.shellWithComposite02.composite;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import com.github.nalukit.nalu.processor.common.MockContext;

public class Composite02Condition
    extends AbstractCompositeCondition<MockContext> {
  
  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }
  
}

package com.github.nalukit.nalu.processor.shell.shellWithComposite02;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;

public class MockCompositeCondition
    extends AbstractCompositeCondition<MockContext> {
  
  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }
  
}

package io.github.nalukit.nalu.processor.shell.shellWithComposite02.composite;

import io.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;

public class Composite02Condition
    extends AbstractCompositeCondition<MockContext> {
  
  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }
  
}

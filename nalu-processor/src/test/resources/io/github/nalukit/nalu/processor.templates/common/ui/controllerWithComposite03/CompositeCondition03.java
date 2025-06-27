package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03;

import io.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import io.github.nalukit.nalu.processor.common.MockContext;

public class CompositeCondition03
    extends AbstractCompositeCondition<MockContext> {
  
  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }
  
}

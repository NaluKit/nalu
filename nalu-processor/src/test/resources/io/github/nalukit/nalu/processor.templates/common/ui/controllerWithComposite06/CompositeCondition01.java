package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite06;

import io.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;

public class CompositeCondition01
    extends AbstractCompositeCondition<MockContext> {

  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }

}

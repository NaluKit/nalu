package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite04;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.AbstractCompositeCondition;
import com.github.nalukit.nalu.processor.common.MockContext;

public class CompositeCondition02
    extends AbstractCompositeCondition<MockContext> {

  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }

}

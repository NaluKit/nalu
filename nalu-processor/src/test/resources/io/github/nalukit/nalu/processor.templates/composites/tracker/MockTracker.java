package io.github.nalukit.nalu.processor.composites.tracker;

import io.github.nalukit.nalu.client.component.annotation.Composite;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.tracker.AbstractTracker;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;

@Composites(@Composite(name = "composite", compositeController = MockComposite.class, selector = "composite"))
public class MockTracker
    extends AbstractTracker<MockContext> {

  @Override
  public void track(String route, String... params) {
    // noop
  }
}

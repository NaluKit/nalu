package com.github.nalukit.nalu.processor.common.event;

import org.gwtproject.event.shared.Event;
import java.lang.Override;

public class MockEvent02
    extends Event<MockEvent02.MockEvent02Handler> {

  public static Type<MockEvent02Handler> TYPE = new Type<>();

  public MockEvent02() {
    super();
  }

  @Override
  public Type<MockEvent02Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MockEvent02Handler handler) {
    handler.onMockEvent02(this);
  }

  public interface MockEvent02Handler {

    void onMockEvent02(MockEvent02 event);

  }

}

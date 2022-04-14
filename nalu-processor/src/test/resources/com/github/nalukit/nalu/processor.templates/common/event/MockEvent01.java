package com.github.nalukit.nalu.processor.common.event;

import org.gwtproject.event.shared.Event;

public class MockEvent01
    extends Event<MockEvent01.MockEvent01Handler> {

  public static Type<MockEvent01Handler> TYPE = new Type<>();

  public MockEvent01() {
    super();
  }

  @Override
  public Type<MockEvent01Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MockEvent01Handler handler) {
    handler.onMockEvent01(this);
  }

  public interface MockEvent01Handler {

    void onMockEvent01(MockEvent01 event);

  }

}

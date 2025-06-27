package io.github.nalukit.nalu.processor.handler.eventhandler.eventHandlerMethodWithTwoParameter;

import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.client.handler.AbstractHandler;
import io.github.nalukit.nalu.client.handler.IsHandler;
import io.github.nalukit.nalu.client.handler.annotation.Handler;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import io.github.nalukit.nalu.processor.common.event.MockEvent02;
import java.lang.Override;

@Handler
public class EventHandlerMethodWithTwoParameter
    extends AbstractHandler<MockContext>
    implements IsHandler {

  @Override
  public void bind() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event01, MockEvent02 event02) {
  }

}

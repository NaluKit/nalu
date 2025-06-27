package io.github.nalukit.nalu.processor.handler.eventhandler.twoEventhandlerOnAHandlerOk02;

import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.client.handler.AbstractHandler;
import io.github.nalukit.nalu.client.handler.IsHandler;
import io.github.nalukit.nalu.client.handler.annotation.Handler;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import java.lang.Override;

@Handler
public class TwoEventHandlerOnAHandlerOk
    extends AbstractHandler<MockContext>
    implements IsHandler {

  @Override
  public void bind() {
  }

  @EventHandler
  public void onMockEvent0101(MockEvent01 event) {
  }

  @EventHandler
  public void onMockEvent0102(MockEvent01 event) {
  }

}

package com.github.nalukit.nalu.processor.handler.eventhandler.twoEventhandlerOnAHandlerOk;

import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.IsHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import com.github.nalukit.nalu.processor.common.event.MockEvent02;
import java.lang.Override;

@Handler
public class TwoEventHandlerOnAHandlerOk
    extends AbstractHandler<MockContext>
    implements IsHandler {

  @Override
  public void bind() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event) {
  }

  @EventHandler
  public void onMockEvent02(MockEvent02 event) {
  }

}

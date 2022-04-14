package com.github.nalukit.nalu.processor.eventhandler.eventhandlerOnAHandlerOK;

import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;

@Handler
public class EventHandlerOnAHandlerOk {


  @EventHandler(MockEvent01.class)
  public void onMockEvent01(MockEvent01 event) {
  }

}

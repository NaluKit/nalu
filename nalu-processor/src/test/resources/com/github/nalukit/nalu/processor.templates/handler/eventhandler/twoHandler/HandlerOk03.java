package com.github.nalukit.nalu.processor.handler.eventhandler.twoHandler;

import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.IsHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.processor.common.MockContext;

@Handler
public class HandlerOk03
    extends AbstractHandler<MockContext>
    implements IsHandler {

  @Override
  public void bind() {
  }

}

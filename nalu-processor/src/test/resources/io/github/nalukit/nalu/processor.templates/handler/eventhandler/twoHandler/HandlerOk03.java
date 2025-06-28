package io.github.nalukit.nalu.processor.handler.eventhandler.twoHandler;

import io.github.nalukit.nalu.client.handler.AbstractHandler;
import io.github.nalukit.nalu.client.handler.IsHandler;
import io.github.nalukit.nalu.client.handler.annotation.Handler;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;

@Handler
public class HandlerOk03
    extends AbstractHandler<MockContext>
    implements IsHandler {

  @Override
  public void bind() {
  }

}

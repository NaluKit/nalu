package com.github.nalukit.nalu.simpleapplication.client.handler;

import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.simpleapplication.client.NaluSimpleApplicationContext;
import com.github.nalukit.nalu.simpleapplication.client.event.StatusChangeEvent;

@Handler
public class SimpleApplicationHandler01
    extends AbstractHandler<NaluSimpleApplicationContext> {

  public SimpleApplicationHandler01() {
  }

  @Override
  public void bind() {
    this.eventBus.addHandler(StatusChangeEvent.TYPE,
                             e -> {
                               // Stupid idea! It should only show, that the event was catched by the handler!
                               System.out.print("new Status:" + e.getStatus());
                             });
  }
}

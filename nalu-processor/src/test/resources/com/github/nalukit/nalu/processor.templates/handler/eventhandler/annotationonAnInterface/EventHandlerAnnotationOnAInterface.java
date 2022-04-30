package com.github.nalukit.nalu.processor.handler.eventhandler.handlerAnnotationOnAInterface;

import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.event.MockEvent;

@EventHandler(MockEvent.class)
public interface EventHandlerAnnotationOnAInterface {
}

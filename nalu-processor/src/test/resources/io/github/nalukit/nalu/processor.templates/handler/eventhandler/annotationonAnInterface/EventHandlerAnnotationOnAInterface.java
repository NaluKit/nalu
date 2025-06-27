package io.github.nalukit.nalu.processor.handler.eventhandler.handlerAnnotationOnAInterface;

import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.event.MockEvent;

@EventHandler(MockEvent.class)
public interface EventHandlerAnnotationOnAInterface {
}

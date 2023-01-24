package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.event.RouterStateEvent;
import org.gwtproject.event.shared.EventBus;
import org.gwtproject.event.shared.SimpleEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterStateEventFactoryTest {

  private EventBus eventBus;

  @BeforeEach
  void setUp() {
    this.eventBus = new SimpleEventBus();
    RouterStateEventFactory.INSTANCE.register(this.eventBus);
    RouterStateEventFactory.INSTANCE.clear();
  }

  @Test
  void testStartRoutingEvent01() {
    this.eventBus.addHandler(RouterStateEvent.TYPE,
                             e -> assertEquals(RouterStateEvent.RouterState.START_ROUTING,
                                             e.getState()));

    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01",
                                                           "param01",
                                                           "param02");
  }

  @Test
  void testStartRoutingEvent02() {
    AtomicInteger i = new AtomicInteger();
    List<RouterStateEvent.RouterState> states = Arrays.asList(RouterStateEvent.RouterState.START_ROUTING,
                                                              RouterStateEvent.RouterState.ROUTING_ABORTED,
                                                              RouterStateEvent.RouterState.START_ROUTING);
    this.eventBus.addHandler(RouterStateEvent.TYPE,
                             e -> assertEquals(states.get(i.getAndIncrement()),
                                             e.getState()));

    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01",
                                                           "param01",
                                                           "param02");
    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route02",
                                                           "param01",
                                                           "param01");
  }
  @Test
  void testStartRoutingEvent03() {
    AtomicInteger i = new AtomicInteger();
    List<RouterStateEvent.RouterState> states = Arrays.asList(RouterStateEvent.RouterState.START_ROUTING,
                                                              RouterStateEvent.RouterState.ROUTING_ABORTED,
                                                              RouterStateEvent.RouterState.START_ROUTING);
    this.eventBus.addHandler(RouterStateEvent.TYPE,
                             e -> {
                               assertEquals(states.get(i.getAndIncrement()),
                                            e.getState());
                             });

    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01",
                                                           "param01",
                                                           "param02");
    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01",
                                                           "param03",
                                                           "param01");
  }

  @Test
  void testStartRoutingEvent04() {
    AtomicInteger i = new AtomicInteger();
    List<RouterStateEvent.RouterState> states = Arrays.asList(RouterStateEvent.RouterState.START_ROUTING,
                                                              RouterStateEvent.RouterState.ROUTING_ABORTED,
                                                              RouterStateEvent.RouterState.START_ROUTING);
    this.eventBus.addHandler(RouterStateEvent.TYPE,
                             e -> {
                               assertEquals(states.get(i.getAndIncrement()),
                                            e.getState());
                             });

    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01");
    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent("/shell/route01",
                                                           "param03");
  }

}
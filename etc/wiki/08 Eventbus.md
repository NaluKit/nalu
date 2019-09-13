# Event Bus
A Nalu application has an eventbus. This enables the application to fire and handle events. The event bus is injected in every handler, filter and controller. So you can easily fire events and listen to them.

Nalu uses the event bus from the `org.gwtproject.events` artifact which is ready to use with j2cl.

TBD: MultiModule Eventbus!
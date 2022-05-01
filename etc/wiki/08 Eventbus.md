# Event Bus
Every Nalu application has an event bus. This enables the application to fire events and handle events. The event bus is injected in every handler, filter and controller.

Nalu uses the event bus from the `org.gwtproject.events` artifact which is ready to use with j2cl.

## Event - Firing & Handling

Nalu uses the `org.gwtproject.events.Event`-module to handle events. This module is a copy of the GWT event module and it is J2CL-ready.

### Event Firing

The event bis is injected in every Nalu controller, filter, loader and handler. To fire an event, use:

```java
this.eventBus.fireEvent(new MyEvent());
```

### Event Handling

To handle an Event, you need to register a method at the event bus:

```java
this.eventBus.addHandler(MyEvent.TYPE, e -> onMyEvent(e));
```

Using this code, it is up to the developer to make sure, that the event handler gets removed. Nalu offer an opportunity to automatically remove event handlers. (This is only needed for controller which have a life cycle like component- and composite-controller.)

By using this code:

```java
super.getHandlerRegistrations().add(this.eventBus.addHandler(MyEvent.TYPE, e -> this.onMyEvent(e)));
```

or (in case you want to register more than one event handler):

```java
super.getHandlerRegistrations().compose(this.eventBus.addHandler(MyEvent01.TYPE, e -> this.onMyEvent01(e)), 
                                        this.eventBus.addHandler(MyEvent02.TYPE, e -> this.onMyEvent02(e)));
```

#### EventHandler-Annotation

Starting with version 2.10.0 Nalu added the oppertonity to add an event handler by using an annotation. Now, you can add en event handler quite easily:

```java
@EventHandler
public void onMyEvent(MyEvent event) {
  // do your stuff here
}
```

The code above will add the method `onMyEvent` to the event bus for the event type: **MyEvent**. In addition Nalu will manage the handler registration. Using hte annotation inside a component- or composite-controller, it will be added to the handler registrations. So in case the controller gets destroyed, the handler will be removed.

In case a class is cached, the implementation makes sure, that all handlers of that cached component will be removed and activated again. The activation will be done just before the `activate`-mehtod is called.  

##### Requirements

To use the feature the method which gets annotated must fulfill the following requirements:

* the method must be **public**
* the method must return **void**
* the method must accept only one parameter
* the parameter of the method needs to be an event (this is the event the method gets registered as handler)
* the **@EventHandler**-annotation can only be used inside the following classes:
  * Nalu component controllers
  * Nalu composite controllers
  * Nalu block controllers
  * Nalu handlers
  * Nalu popup controllers
  * Nalu error popup controller
  * Nalu shells
  * Nalu filters
  * Nalu tracker

**Note:** Using the **@EventHandler** annotation in other classes has no effect!

## Event Bus & Multi Module Feature
In a multi module scenario you will have also an application wide event bus. This means modules can communicate with each other.

## NaluApplicationEvent
To avoid a common module containing common classes (in our case: events) we need a solution to fire and handle events in different modules. Nalu provides a `NaluApplicationEvent`-class for the inter-module communication.
```java
public class NaluApplicationEvent
    extends Event<NaluApplicationEvent.NaluApplicationEventHandler> {

  public static Type<NaluApplicationEvent.NaluApplicationEventHandler> TYPE = new Type<>();

  private String              event;
  private Map<String, Object> store;

  private NaluApplicationEvent() {
    super();
    this.store = new HashMap<>();
  }

  /**
   * Creates a new NaluMessageEvent.
   *
   * @return new Message event
   */
  public static NaluApplicationEvent create() {
    return new NaluApplicationEvent();
  }

  /**
   * Sets the message type
   *
   * @param event message type of the event
   * @return instance of the event
   */
  public NaluApplicationEvent event(String event) {
    this.event = event;
    return this;
  }

  /**
   * Adds data to the data store.
   *
   * <b>Keep in mind, all parameters will be stored as objects!</b>
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public NaluApplicationEvent data(String key,
                                   Object value) {
    this.store.put(key,
                   value);
    return this;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type of the event
   */
  public String getEvent() {
    return event;
  }

  /**
   * Returns the value for the given key.
   *
   * <b>Keep in mind to cast the return value.</b>
   *
   * @param key key of the stored parameter
   * @return the value of the stored parameter using the key or null
   */
  public Object get(String key) {
    return this.store.get(key);
  }

  @Override
  public Type<NaluApplicationEvent.NaluApplicationEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NaluApplicationEvent.NaluApplicationEventHandler handler) {
    handler.onNaluApplicationEvent(this);
  }

  public interface NaluApplicationEventHandler {

    void onNaluApplicationEvent(NaluApplicationEvent event);

  }

}
```
This event is available in all Nalu modules. It has an 'event'-'String for storing the event - This can be used as an event type - and a data store to add additional data to the event.

## Create a NaluApplicationEvent
```java
NaluApplicationEvent.create()
                    .event("StatusEvent")
                    .data("message",
                          "Please enter your credentials!"));
```
The above code creates a `NaluApplicationEvent` with the event type 'StatusEvent' and adds a 'message' with the text 'Please enter your credentials!'.

All you need to do is to fire the event like this:
```java
this.eventBus.fireEvent(NaluApplicationEvent.create()
                                            .event("StatusEvent")
                                            .data("message",
                                                  "Please enter your credentials!"));
```

## Handling a NaluApplicationEvent
To handle the NaluApplicationEvent, you need something like this:
```java
this.eventBus.addHandler(NaluApplicationEvent.TYPE,
                         e -> {
                           if ("StatusEvent".equals(e.getEvent())) {
                             component.setStatus((String) e.get("message"));
                           }
                         });
```
Once you catch a `NaluApplicationEvent`, you need first to check the event type. In our example, we will handle a 'StatusEvent', so we check if the event is 'StatusEvent'. In case it is an 'StatusEvent' we expect a data 'message'. We can ask the event for a data called 'message' by calling `e.get("message").

**Keep in mind, the `e.get("myKey")` will always return an `Object`. So you need to cast the return value. And: keep in mind,  a requested value might be null!**
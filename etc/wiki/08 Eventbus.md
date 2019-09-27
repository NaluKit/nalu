# Event Bus
Every Nalu application has an event bus. This enables the application to fire events and handle events. The event bus is injected in every handler, filter and controller.

Nalu uses the event bus from the `org.gwtproject.events` artifact which is ready to use with j2cl.

**Note: As long as the module `gwt-events` ins not in Maven Central, Nalu uses copies of the classes.**

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

**Keep in mind, the `e.get("myKey")` will always return an `Object`. So you need to cast return value. And a requested value might be null!**
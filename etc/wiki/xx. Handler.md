# Handler
Nalu supports handlers. A handler behaves like a controller, but has no visiual component. A Handler can only be triggered by an event. The following instances will be injected in a handler:

* context
* event bus
* router

Nalu will create handlers at application start. To trigger a handler, an event has to be fired.

## Defining a Handler
To create a handler, you have to:

* extend AbstractHandler<C>
   - C: type of the context

By extending AbstractHandler you will have access to the allowing instances:

* C context: instance of the application context (Singleton)
* eventbus: instance of the application wide event bus
* router: instance fo the router

To tell Nalu, that this class is a handler, you have to annotate the class with `Handler`. Nalu will automatically create an instance for each class annotated with `@Handler` at application start.

A handler requires a

* a public, zero-argument constructor
* annotate the handler with `@Handler`

Here is an example of a controller:
```Java
@Handler
public class MyHandler
  extends AbstractHandler<MyApplicationContext> {

  public MyHandler() {
  }

  @Override
  public void bind() {
    this.eventBus.addHandler(MyEvent.TYPE,
                             e -> {
                               // your code here
                             });
  }
```

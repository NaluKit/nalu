# Error Handling
There are several validations inside Nalu, that can cause an error:

* a routing loop

* an illegal route

* using an non existing selector

In addition, the error handling can be used to display application errors.

Nalu will report errors by firing the `NaluErrorEvent`.

**Important Note:** Starting with version 2.0.0 the error route has been removed!

## Creating Nalu Error Event
In case you want to show an application error using the Nalu error mechanism, you need to define a Nalu error event first.

Creating a Nalu error event:
```java
NaluErrorEvent.createApplicationError()
              .id("myErrorId")
              .route("/application/person/search")
              .message("Oh, nothing wrong ... only wonna show a nice error dialog!")
              .data("key01",
                    "first parameter")
              .data("key02",
                    "second parameter");
```

To create a Nalu error event, call `NaluErrorEvent.createApplicationError()`. This will create a Nalue error event with a error type of `APPLICAITON_ERROR`. (Nalu internal error events will have the error type `NALU_INTERNAL_ERROR`.

**Do not use the `NaluErrorEvent.createNaluError()`-method!**

You can add an error id using `id`, a route using the `route("myRoute")`-method. The message can be added using the `message("myErrorMessage")`-method. Also, you can add as much informations as you like. To add an information call the `data("key", "value")` -method.

To retrieve informations from the Nalu error event, you will have several getter-methods:

* **getErrorEventType:** will return the error type
* **getErrorId:** will return the error id
* **getMessage:** will return the error message
* **getRoute:** will return the route
* **get(String key):** will return the value for a defined key

In case of a Nalu error the store does not contain additional informations.

## Handling Nalu Error event
To handle a Nalu error event there are two ways to go:

1. using the ErrorPopUpController
2. using a handler, that catches the event and route to an error page.

Solution two will be the easiest way to migrate from Nalu version 1.x.

### Using the ErrorPopUpController
Nalu provides a special controller to handle Nalu error event, the 'ErrorPopUpController'. This will show a popup in case of an error.

The 'ErrorPopUpController' is a `PopUpController` based controller which catches the `NaluErrorEvent` and inject the error data into the controller.

To define a 'ErrorPopUpController' use this code:
```java
@ErrorPopUpController(component = ErrorComponent.class,
                      componentInterface = IErrorComponent.class)
public class ErrorController
    extends AbstractErrorPopUpComponentController<MyContext, IErrorComponent>
    implements IErrorComponent.Controller {

  public ErrorController() {
  }

  @Override
  public void onBeforeShow() {
    this.component.clear();
  }

  @Override
  protected void show() {
    this.component.edit(this.errorEventType,
                        this.route,
                        this.message,
                        this.dataStore);
    this.component.show();
  }

}
```
A ErrorPopUpController must extend the `AbstractErrorPopUpComponentController<C, V>`-class and must be annotated with `@ErrorPopUpController`. `@ErrorPopUpController` takes two attributes:

1. the component interface class
2. the component class.

To handle the Nalu error event, the `show()`-method needs to be overwritten. Inside this method, set up the data inside the component and call the `show()`-method. In case you would like to do something before the `show()`-method, you can overwrite the  `onBeforeShow()`-method.

**You can only define one ErrorPopUpController per application!**

## Using a Handler
In case you want to show a new screen to display the error (or you are looking for an easy way to migrate from Nalu v1.x) use the handler solution.

To do so:

1. create a handler

2. add an event handler that listens for the `NaluErrorEvent`

3. in case the event is fired, store the `ErrorInfo`-instance inside your context

4. route to the error page.

This is an example of an ErrorHandler:
```java
@Handler
public class ErrorHandler
    extends AbstractHandler<ApplicationContext> {

  public ErrorHandler() {
  }

  @Override
  public void bind() {
    this.eventBus.addHandler(NaluErrorEvent.TYPE,
                             e -> handleNaluErrorEvent(e));
  }

  private void handleNaluErrorEvent(NaluErrorEvent e) {
    this.context.setErrorInfo(e.getErrorInfo());
    this.router.route(Routes.ROUTE_ERROR);
  }

}
```
The context looks like that:
```java
public class ApplicationContext
    implements IsContext {

  private ErrorInfo errorInfo;

  public ApplicationContext() {
  }

  public ErrorInfo getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(ErrorInfo errorInfo) {
    this.errorInfo = errorInfo;
  }

}
```


See the 'xxx'-exammple:


# Error Handling
There are several validations inside Nalu, that can cause an error:

* a routing loop

* an illegal route

* using an non existing selector

In addition, the error handling can be used to display application errors.

Nalu supports two ways of error handling.

1. based on an error route

2. based on an error event.

To use the error route feature, define an error route inside the `@Application`-annotation:
```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class,
             routeError = "/errorShell/error")
interface MyApplication
    extends IsApplication {
}
```
In this case Nalu will use the error route to communicate error.

Defining the `@Application`-annotation without an error route, will tell Nalu to fire an error event:
```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
    extends IsApplication {
}
```


## Error Route
In case the application should use the error route feature, add an error route to the `@Application`-annotation:

```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class,
             routeError = "/errorShell/error")
interface MyApplication
    extends IsApplication {
}
```

This tells Nalu to use the error for presenting errors.

in case an internal Nalu error occurs, Nalu wil use the error route and route the error page. The current error is stored in a error model inside the router. Using `this.router.getNaluErrorMessage()` will give access to the error object.

**Once an error is displayed, the error object should be reseted by calling `this.router.clearNaluErrorMessage()`.**

### Using Error Route to display application errors (since 1.2.1)
Starting with version **1.2.1** Nalu supports using the error route to display application error messages. To do so, Nalu offers several new methods:
* **clearApplicationErrorMessage**: clears the application error message

* **setApplicationErrorMessage**: sets the application error message. This methods accepts two parameters:
  - **errorType**: a String that indicates the type of the error (value is to set by the developer)
  - **errorMessage**: the error message that should be displayed

* **getApplicationErrorMessage**: which returns the application error message.

In cases where an application error message and an error message from Nalu exists, you can use **getErrorMessageByPriority** to get the error message with the higher priority, which will always return the Nalu error message. Otherwise this method returns the application error message.

**Once an error is displayed, the error object should be reseted by calling `this.router.clearApplicationErrorMessage()`.**

## Error Event (since 2.0.0)
In case the application should use the error event feature, do not add an error route to the `@Application`-annotation:

```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
    extends IsApplication {
}
```

This tells Nalu to use the error event for presenting errors.

In case of an error Nalu will fire the `NaluErrorEvent`. The `NaluErrorEvent` provides four informations:
1. **errorEventType**: this defines the error type of the event. In case it is an error created by Nalu, the variable will contain 'NaluError' as value.
2. **route**: contains the error where the error occurs.
3. **message**: contains the error message.
4. **store**: contains additional error messages. (In case of a Nalu error the store does not contain additional infomrations)



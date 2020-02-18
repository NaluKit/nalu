# Application Loader
Nalu provides a mechanism to execute asynchronous actions during application start.

This is a good place, to

* load data from the server and store them f. e.: in the context or a cache for later use
* load application wide style sheets and provide them via the application context
* do anything else, you want to do at application start ...

Once all asynchronous action(s) are executed, the control will be return to Nalu by calling `finishLoadCommand.finishLoading();`.

## Creating an Application Loader
To create an application loader, just create a class, that extend `AbstractApplicationLoader`. Extending `AbstractApplicationLoader` will force the implementation to add a `load`-method.

* **load**: this method will be called during application start and before the initial route is called. When all loadings are done, the method `finishLoadCommand.finishLoading();` has to be called to return the control to Nalu. After returning the control to Nalu, Nalu will continue the start process of the application.

Example of an application loader:

```java
public class MyApplicationLoader
  extends AbstractApplicationLoader<MyContext> {

  @Override
  public void load(FinishLoadCommand finishLoadCommand) {

    // do the application loading

    finishLoadCommand.finishLoading();
  }
}
```
A loader has a reference to the instances of:

* the event bus
* the application context
* the router (since v1.2.1)

**Important:**

**Do not use the router inside the application loader! Do not route.**

**Because the loader is called during the start phase of a Nalu application, some things might not be set correctly. Use the router instance only to inject something for later use.**

## Setting a loader
To set a loader for an application, you need to add your loader to the @Application annotation and set the value of the loader attribute to your loader class.

```java
@Application(loader = MyApplicationLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
  extends IsApplication {
}
```

## Handling application loader failure
In case a loading error occurs that prevent the application from starting, it is a good practice to inform the user about the error using a popup (or something else) and route the application to a technical error page.


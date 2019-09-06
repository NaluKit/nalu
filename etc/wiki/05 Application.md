# Application
Every Nalku application needs one application interface. The application interface defines the basic behavoir of Nalu.

## Application interface
To implement Nalu in your application, first create an application interface:
```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class,
             routeError = "/errorShell/error")
interface MyApplication
    extends IsApplication {
}
```
The application interface must extends `IsApplication` and needs the `@Application` annotation.

The attributes of the Application annotation are:

* **loader**: the application loader will be executed at application start. (see: [Application Loader](xxx). This attribute is optional.

* **startRoute**: The start route is the initial route that is called in case the application is called without a hash. If the application is called with a hash, the startRoute-attribute has no effect.

* **context**: the application context is a singleton that will be injected in every filter, controller and handler of the application (see: [Context](xxx)).

* **routeError**: the error route is used to show an error page if Nalu detects some issues. This attribute is optional. (see: [Error handling](xxx))

* **history**: if **true** Nalu supports history, so the application can reload. Otherwise no hash will be created and the url stays untouched. This might be useful, in case the application should never reload or will be used embedded inside another web side. (default: **true**)

* **useHash**: if **true** Nalu uses a hash token, otherwise a hashless token. (default: **true**)

* **useColonForParametersInUrl**: if **true** Nalu uses a ':' before a parameter inside the url (default: **false**)

* **stayOnSide**: if **true** Nalu will - in case of an empty hash -  add an new history entry using the hash of the start route

In case **useHash** is used with the value **false**, you need to create a filter or something else to handle a bookmark correct. This filter should evaluate the relative path of the application and return a parameter named **url** with this value.

## Start Route
The start route is the route, that will be used in case the application is called without a hash.

Minimal requirements for a start route are:

* needs a shell
* needs a part to identify a controller

**Important note: a start route can not have a parameter!**









==> ab hier nach application








## Error Route
In case Nalu

* detects a routing loop
* an illegal route
* does not find a selector

Nalu will route the error page. The current error is stored in a error model inside the router. Using `this.router.getNaluErrorMessage()` will give access to the error object.

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


## Filter Annotation
Nalu allows you to use filters to stop routings in order to interrupt a route before it is handled and redirect to another route instead.

To create a filter, add the ```@Filter````-annotation to the application interface.

Nalu supports the use of more than one filter.

### Creating Filters
To create a filter, you need to:

1. implement the ```IsFilter```-interface
2. override the ```filter```-method: this method will allow to stop a routing or not. If filterEvent method returns false, then the route is stopped, otherwise, it is forwarded to the router.
3. override the ```redirect```-method: in case the routing is stopped, the route return by the method will be routed.
4. override the ```parameters```-method: the parameters of the route. In case there are no parameters, return ```String[]{}```.

Nalu will inject the context into a filter.

### Adding Filters
Once you have created a filter, you need to tell Nalu to use it. This will be done thanks to the ```@Filters```-annotation that annotates your application interface.

```Java
@Filters(filterClasses = MyFilter.class)
```

**The @Filters annotation will only be handle if the interface is also annotated with @Application!**

The annotation @Filters has the following attributes:

* **filterClasses**: set one or several filters to use. An instance will be created for each class specified.


## Debug Annotation
Nalu integrates a log feature that let you trace the routes handled and controllers used.

To activate the log feature, you need to annotate your application class with @Debug:
```Java
...
@Debug()
interface MyApplication
    extends IsApplication {
}
```

**The @Debug annotation will only be handle if the interface is also annotated with @Application!**

```@Debug```-annotation has the following attributes:

* **logLevel** (optional, default: SIMPLE): define the level of log:
    * SIMPLE (minimal logging)
    * DETAILED (more detailed logging)
* **logger**: define the class of the logger to use depending on the selected plugin

Every plugin provides a default logger. In case you do not want to create an own logger, use:

* **gwt-plugin-elemental2**: ```DefaultElemental2Logger```.class
* **gwt-plguin-gwt**: ```DefaultGWTLogger```.class


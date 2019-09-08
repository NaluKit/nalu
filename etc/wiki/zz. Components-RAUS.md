



#TBD








# Components
Befor we dig deeper into Nalu some basics you need to know when working with Nalu.



















## Context
The context can be used to store application wide information (f.e.: such as user name, etc). A instance of the context is injected in every filter, handler and controller. This is a good place to store general needed data.

Starting with version 2.0.0 a new `AbstractContext`-class will be available.


## Component
The component contains the visible part of a screen. It can be compared to the view of the MVP pattern. A component will be automatically created by the framework and injected into the controller. The use of the component class respects the view delegate pattern.

## Controller
The controller can be compared with the presenter of the MVP pattern. It must be annotated with ```@Controller```. The referenced component is created by the framework and injected into the controller.

A controller annotation may look like this:

```java
@Controller(route = "/application",
            selector = "content",
            componentInterface = IMyComponent.class,
            component = MyComponent.class)
```

The attributes of the controller annotation are all required.

The attributes are:

* **route**: defines the route, which will make the component visible.
* **selector**: defines the place inside the DOM where the component will be added.
* **componentInterface**: is the reference to the component interface
* **component**': is the reference of the component

A route **'/'** indicates, that the controller will be executed at application start.


## Controller Lifecycle
Every time a routing happens, a new controller will be created and the ```start```-method is called.
Before the component will be removed from the DOM, the ```mayStop```-method will be called. This enables the application to interrupt the routing by returning a String value. Normally a String is return and will be used as confirmation message in a confirmation dialog.

In case a routing occurs, the stop()-method of the active controller is called before the start-method of the newly created controller is called.

Nalu will never reuse a controller instance.



## Handler
Handlers are classes without a component and can only be triggered by firing events. All handlers of a Nalu application will be created at application start. Handlers are great to deal with data, validate, etc. Usually handlers will catch events to get triggered. To give the control back to the application use a callback.

## Loader (optional)
Nalu provides a loader that will be executed on application start. This is a good place to load static and meta data form the server.


## Router
The router is the core of a Nalu application. It handles every routing of the application. The router is injected into every controller and handler. It enables routing inside the application.

Navigation is done by calling:

```Java
router.route("newRoute", [parameters]);
```

The router also provides a hash, so, that it can be used with anchors.

## Caching
Nalu provides a caching mechanism. This allows to store a controller/component for a route. Next the route will be used, Nalu restores the cached controller/component instead of creating a new controller and component.

To tell Nalu to cache a controller/component, use the `router.storeInCache(this)`-command inside the controller. Now, caching for this route is active. To stop caching, call `router.removeFromCache(this)`. To remove everything from cache, call `router.clearCache()`.

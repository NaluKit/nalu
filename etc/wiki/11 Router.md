# Router
The router is the main component inside a Nalu application. It is used to navigate inside the application, generate a url, get the start parameter and cache controllers.

The instance of the router is injected into:

* every controller
* every filter
* every handler.

The router can be used to:

* route inside the navigation
* to generate a route
* provides url parameters from the start url
* manages the caching

## Routing
To navigate inside a Nalu application you can use the `route`-method. The `route`-method accepts a route and a number of parameters (0 - n). For a successful navigation, the route must be valid. This means, there must be at least one controller, that accepts the route.

A route without parameters can be called like this:
```java
  this.router.route("myShell/myRoute01/myRoute02");
```
In case the route has parameters, add them as to the method:
```java
  this.router.route("myShell/myRoute01/myRoute02", parameter01, parameter02);
```
In this case Nalu will add the parameters at the end of the route. The resulting route will look like this:
```
   myShell/myRoute01/myRoute02/parameter01/parameter02
 ```
To set a parameter inside the route, use a '*' inside the route:
```java
  this.router.route("myShell/myRoute01/*/myRoute02", parameter01, parameter02);
```
This will create the following route:
```
   myShell/myRoute01/parameter01/myRoute02/parameter02
```
Using this method:
```java
  this.router.route("myShell/myRoute01/" + parameter01 + "/myRoute02/" + parameter02);
```
will create a similar result.

**Attention:**

If you are using `useColonForParametersInUrl = true` inside the Application annotation, you need to add a ':' before every parameter:
```java
  this.router.route("myShell/myRoute01/:" + parameter01 + "/myRoute02/:" + parameter02);
```
In case the routing is used in this way:
```java
  this.router.route("myShell/myRoute01/*/myRoute02", parameter01, parameter02);
```
there is no need to add ':' before parameters, because Nalu will do that for you.

### Routing without calling the mayStop-method (since 2.0.1)
Starting with version 2.0.1 Nalu offers a new method to route. The name of the new method is `forceRoute`. You can use the `forceRoute`-method similar to the `forceRoute`-method. The only difference is, that the `forceRoute`-method will not call the `mayStop`-method of currently active controllers and composites.

This might be useful in case of an error and routing to a central error page or a start-page.

### Stealth Routing without calling the mayStop-method (since 2.5.0)
Starting with version 2.5.0 Nalu offers a new method to route. The name of the new method is `forceStealthRoute`. You can use the `forceStealthRoute`-method similar to the `forceStealthRoute`-method. The main difference is, that the `forceStealthRoute`-method will not update the hash and will not call the `mayStop`-method of currently active controllers and composites. The routing events will be fired!

### Stealth Routing (since 2.5.0)
Starting with version 2.5.0 Nalu offers a new method to route. The name of the new method is `stealthRoute`. You can use the `stealthRoute`-method similar to the `stealthRoute`-method. The main difference is, that the `stealthRoute`-method will not update the hash. The routing events will be fired!

This might be useful in case you have to implement a dispatcher controller and don't want to the dispatcher hash inside the history.

### Fake Routing (since 2.7.0)
Starting with version 2.7.0 Nalu offers a new method to route. The name of the new method is `fakeRoute`. The `fakeRoute`-method will update the hash, **but does not route**. No controller method will be executed. The routing events will be fired!

This might be useful in case you want to update the hash inside the URL but don't want to do a complete routing.

### Note on routes
Defining routes in your application needs some attention. Otherwise you will be surprised by some unexpected results.

For example:

* controller one has a route like this: **/shell/route01/:parameter01**
* controller two has a route like this: **/shell/route01/route02**

In case the hash is something like that: **/shell/route01/1**, Nalu will create controller one and inject '1' using the `setParameter01`-method. Controller two will not be created.
Now, in case we have a route like this: **/shell/route01/route02**, Nalu will also create controller one and - this time - inject 'route02' using the `setParameter01`-method. Controller two will not be created!

## Generating a Route
In case there is a need for a route inside a link, you can use the `generate`-method of the router. The method accepts a route and a list of parameters (0 - n) and will return the route. Calling the method with the following code:
```java
  this.router.route("myShell/myRoute01/*/myRoute02", parameter01, parameter02);
```
will return a String that looks like that:
```text
   myShell/myRoute01/parameter01/myRoute02/parameter02
```

## Providing Start Parameters (since 1.1.0)
In case the application is called with parameters, Nalu provides a method inside the router to retrieve this parameter. Calling `getStartQueryParameters()` without parameters will return a `Map<String, String>` containing all start parameters.

For example, if the application is called with this url:
```text
http://localhost:8080/myApp/index.html?param1=value01&param2=value02
```
and you want to access the `param2`, you can use this code:
```java
  String value = this.router.getStartQueryParameters().get("param1");
```
to get the value of *param1*.

## Caching
Nalu offers a caching feature. With this feature controllers and/or composites (since v1.3.2) can be cached. This means, once Nalu has routed to a controller and this controller is cached, the instance of this controller (and of course the view) is cached and will be reused in case Nalu routes to this controller again.

To cache a controller, use: `this.router.storeInCache(this);` inside a controller.

To remove the controller from the cache, so that it will be created again, use: `this.router.removeFromCache(this);`.

You can also cache composites.

For more information about caching, take a look [here](xxx).

## RouterStateEvent (since 1.3.1)
Nalu will fire a `RouterStateEvent` in case a routing occurs. The `RouterStateEvent` provides two pieces of information:

1. state of routing

2. the current route

### State of Routing
The `RouterStateEvent` will be fire in three cases:

1. a Routing is initiated: the value of the state attribute will be: **START_ROUTING**

2. a Routing is aborted: the value of the state attribute will be: **ROUTING_ABORTED**

3. a Routing is done: the value of the state attribute will be: **ROUTING_DONE**

You will see the state **ROUTING_ABORTED** only in case a routing is aborted. In this case there will no event containing the state **ROUTING_DONE**!

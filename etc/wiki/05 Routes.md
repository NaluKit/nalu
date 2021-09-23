# Routes
To navigate between sides and for the transport of parameters, Nalu uses routes. A route is a String. Parts of the route are separated with '/'.

Example of a correct route:
```
/myShell/routePart01/parameter01
```

Let's look at the route.

The first part of the route is always the name of the shell. Nalu will use this part of the route and searches for a shell with that name. In case the name of the shell has not change compared to the shell from the route before, Nalu does nothing.

In case the name of the shell changed, Nalu will remove the last shell and replace it with the new shell.

Next Nalu will look for controllers, that match the route part. Matching controllers will be created and added to the shell.

Parameters inside a route are optional. In case a route has parameters, Nalu will inject the parameters into the newly created controllers. Nalu will identify parameters inside a route in case the part of the route start with ':' or the part is inside '{}'.

Of cause, the route parts can be a combination of several parts. So, routes might look like this:
```
/myShell/routePart01
/myShell/routePart01/routePart02
/myShell/routePart01/routePart02/routePart03
```

Parameters inside a route can be added to the end of the route or inside the route. The only limitation for parameters is: **parameters can never be the first part of a route!**

These are all legal routes:
```
/myShell/routePart01/parameter01
/myShell/routePart01/routePart02/:parameter01
/myShell/routePart01/:parameter01/routePart02/{parameter02}
/myShell/routePart01/{parameter01}/routePart02/{parameter02}/routePart03
```


## Parameters
In case a route has parameters, they have to be added to the route of the `@Controller`-annotation:
```java
@Controller(route = "/application/myRoute/:parameterName",
            selector = 'content',
            componentInterface = IMyComponent.class,
            component = MyComponent.class)
```
To enable parameters, just add: **/:parameterName** to the route. Parameters can be at every where, except as first part of a route!  It is possible to have more than one parameter. If a route contains a parameter, it is necessary, that the controller implements a method called: `set[ParameterName](String value)` and annotate this method with `@AcceptParameter("parameterName")`-annotation.

**The type of the parameter is always String.**

You can add as much parameters as you like. Every parameter has to Start with '/:'.

**Note**

Characters like '?', '&' and '#' are characters that can not be used inside a route, nor as a parameter value. A parameter value containing a '/'-character is also illegal. If you like to use this values, you need to escape them.
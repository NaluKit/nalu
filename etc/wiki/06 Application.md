# Application
Every Nalu application needs one application interface. The application interface defines the basic behavoir of Nalu.

## Application interface
To implement Nalu in your application, first create an application interface:
```Java
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
    extends IsApplication {
}
```
The application interface must extends `IsApplication` and needs the `@Application` annotation.

The attributes of the Application annotation are:

* **startRoute**: The start route is the initial route that is called in case the application is called without a hash. If the application is called with a hash, the startRoute-attribute has no effect.

* **loader**: the application loader will be executed at application start. (see: [Application Loader](xxx). This attribute is optional.

* **context**: the application context is a singleton that will be injected in every filter, controller and handler of the application (see: [Context](xxx)).

* **history**: if **true** Nalu supports history, so the application can reload. Otherwise no hash will be created and the url stays untouched. This might be useful, in case the application should never reload or will be used embedded inside another web side. (default: **true**)

* **useHash**: if **true** Nalu uses a hash token, otherwise a hashless token. (default: **true**)

* **useColonForParametersInUrl**: if **true** Nalu uses a ':' before a parameter inside the url (default: **false**)

* **stayOnSide**: if **true** Nalu will - in case of an empty hash -  add an new history entry using the hash of the start route

In case **useHash** is used with the value **false**, you need to create a filter or something else to handle a bookmark correct. This filter should evaluate the relative path of the application and return a parameter named **url** with this value.

### Start Route
The start route is the route, that will be used in case the application is called without a hash.

Minimal requirements for a start route are:

* needs a shell
* needs a part to identify a controller

**Important note: a start route can not have a parameter!**

Using only a shell as start route will produce an error.

## Filter Annotation
Nalu allows you to use filters to stop routings in order to interrupt a route before it is handled. In case a route is interrupted, you can redirect to another route.

To create a filter, add the `@Filter`-annotation to the application interface.

Nalu supports the use of more than one filter.

### Creating Filters
To create a filter, you need to:

1. implement the `IsFilter`-interface
2. override the `filter`-method: this method enables you to stop a routing. If filterEvent method returns **false**, the route is stopped, otherwise, it is forwarded to the router.
3. override the `redirect`-method: in case the routing is stopped, the route returns the new route to go to.
4. override the `parameters`-method: the parameters of the route. In case there are no parameters, return `String[]{}`.

Nalu will inject the context into a filter.

Example of a filter:
```java
import java.util.Objects;public class MyFilter
    extends AbstractFilter<MyContext> {

  @Override
  public boolean filter(String route,
                        String... parms) {
    // checking f.e. a route
    if ("/myShell/myRoute01".equals(route)) {
      // interrupt routing
      return false;
    }
    // checking for example a parameter
    if (parmslength > 0) {
      if ("Bart".equals(parms[0])) {
        // interrupt routing
        return false;
      }
    }
    // routing ok!
   return true;
  }

  @Override
  public String redirectTo() {
    return "/redirectShell/redirectRoute";
  }

  @Override
  public String[] parameters() {
    return new String[] {};
  }

}
```

### Adding Filters
Once you have created a filter, you need to tell Nalu that there are filter and to use the. This can be done thanks to the ```@Filters```-annotation, which can be used on your application interface.

```Java
@Filters(filterClasses = { MyFilter01.class, MyFilter02.class })
interface MyApplication
    extends IsApplication {
}
```

**The @Filters annotation will only be handle if the interface is annotated with @Application!**

The annotation @Filters has the following attributes:

* **filterClasses**: set one or several filters to use. An instance will be created for each class specified.

In case you have more than one filter, tilters will be executed in a unconditional order!

## Debug Annotation
Nalu integrates a log feature that let you trace the routes handled, controllers used, fired events, etc. The debug messages will be displayed using the browser's console.

**This feature is only available during development!***

To activate the log feature, you need to annotate your application class with `@Debug`:
```Java
@Debug()
interface MyApplication
    extends IsApplication {
}
```

**The @Debug annotation will only be handle if the interface is also annotated with @Application!**

`@Debug`-annotation has the following attributes:

* **logLevel** (optional, default: SIMPLE): define the level of log:
    * SIMPLE (minimal logging)
    * DETAILED (more detailed logging)
* **logger**: define the class of the logger to use depending on the selected plugin

Every plugin provides a default logger. In case you do not want to create an own logger, use:

* **gwt-plugin-elemental2**: `DefaultElemental2Logger`.class
* **gwt-plguin-gwt**: `DefaultGWTLogger`.class


# Application
Every Nalu application needs one application interface. The application interface defines the basic behavoir of Nalu.

## Application interface
To implement Nalu in your application, first create an application interface:
```java_holder_method_tree
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
    extends IsApplication {
}
```
The application interface must extend `IsApplication` and needs the `@Application` annotation.

The attributes of the Application annotation are:

* **startRoute**: The start route is the initial route that is called in case the application is called without a hash. If the application is called with a hash, the startRoute-attribute has no effect.

* **loader**: the application loader will be executed at application start and before loading the modules. (see: [Application Loader](https://github.com/NaluKit/nalu/wiki/07.-Application-Loader). This attribute is optional.

* **postLoader**: the application post loader will be executed at application start and after all modules have been added and all module loaders have been executed. (see: [Application Loader](https://github.com/NaluKit/nalu/wiki/07.-Application-Loader). This attribute is optional.

* **context**: the application context is a singleton that will be injected in every filter, controller and handler of the application (see: [Context](https://github.com/NaluKit/nalu/wiki/10.-Context)).

* **history**: if **true** Nalu supports history, so the application can reload. Otherwise no hash will be created and the url stays untouched. This might be useful, in case the application should never reload or will be used embedded inside another web side. (default: **true**)

* **useHash**: if **true** Nalu uses a hash token, otherwise a hashless token. (default: **true**)

* **useColonForParametersInUrl**: if **true** Nalu uses a ':' before a parameter inside the url (default: **false**)

* **stayOnSide**: if **true** Nalu will - in case of an empty hash -  add an new history entry using the hash of the start route

* **alertPresenter**: Tells Nalu to use a custom alert - this parameter is optional

* **confirmPresenter**: Tells Nalu to use a custom confirm - this parameter is optional

* **illegalRouteTarget**: Tells Nalu to use this route instead showing an error message  - this parameter is optional

In case **useHash** is used with the value **false**, you need to create a filter or something else to handle a bookmark correct. This filter should evaluate the relative path of the application and return a parameter named **url** with this value.

### Start Route
The start route is the route, that will be used in case the application is called without a hash.

Minimal requirements for a start route are:

* needs a shell
* needs a part to identify a controller

**Important note: a start route can not have a parameter!**

Using only a shell as start route will produce an error.

### Custom alert and confirm
Nalu offers the possibility to add a custom alert and confirm dialog. In case it is set, Nalu will use the custom dialog instead of the default dialog from the plugin.

A custom alert needs to implement the `IsCustomAlertPresenter` and a custom confirm dialog needs to implement the `IsCustomConfirmPresenter`.

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
import java.util.Objects;

public class MyFilter
    extends AbstractFilter<MyContext> {

  @Override
  public boolean filter(String route,
                        String... params) {
    // checking f.e. a route
    if ("/myShell/myRoute01".equals(route)) {
      // interrupt routing
      return false;
    }
    // checking for example a parameter
    if (paramslength > 0) {
      if ("Bart".equals(params[0])) {
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
Once you have created a filter, you need to tell Nalu that there are filter and to use the. This can be done thanks to the `@Filters`-annotation, which can be used on your application interface.

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

## Logger Annotation (since version v2.1.0)
Nalu has a logger feature to log informations to the browser console or to the server (service needs to be provided).

To activate the log feature, you need to annotate your application class with `@Logger`:
```Java
@Logger(clientLogger = DefaultElemental2ClientLogger.class,
        logger = MyLogger.class)
interface MyApplication
    extends IsApplication {
}
```

**The @Logger annotation will only be handle if the interface is also annotated with @Application!**

`@logger`-annotation has the following attributes:

* **clientLogger**: defines the class of the client side logger to use depending on the selected plugin
* **logger**: defines the class of the logger which can be used to send log messages to the server

Every plugin provides a default client logger. In case you do not want to create an own logger, use:

* **gwt-plugin-elemental2**: `DefaultElemental2Logger`.class
* **gwt-plugin-elemento**: `DefaultElementoLogger`.class
* **gwt-plugin-gwt**: `DefaultGWTLogger`.class

The logger needs to implement the IsLogger-interface and looks like this:
```java
public class MyLogger
    extends AbstractLogger<MyContext> {
  
  @Override
  public void log(List<String> messages,
                  boolean sdmOnly) {
    LoggingServiceFactory.INSTANCE.log(messages)
                                  .onSuccess(response -> {
                                  })
                                  .onFailed(failed -> {
                                  })
                                  .send();
  }
  
}
```
This example uses [Domino-rest](https://github.com/DominoKit/domino-rest) to send the messages to server.

A log message can be triggered by firing a `LogEvent`.

## Version and Build Time
Nalu supports setting a version String using an annotation and storing the build time. To get this feature, the context needs to extend `AbstractModuleContext` and inside the application interface you have to add the **Version**-annotation.

```java_holder_method_tree
@Application(loader = MyLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
@Version("1.0.1")             
interface MyApplication
    extends IsApplication {
}
```

The example above will set the version of the application to '1.0.1'.
To access the application version, use: `context.getApplicationVersion()`. To access the build time call `context.getApplicationBuildTime()`.

**Important:** \
To access the  build time, your context need to extend the `AbstractModuleContext`-class.

It is possible to override the value from the version annotation from the command line. Nalu will look for a property called "nalu.application.version". If the property exists, Nalu will use this value. This will only work, in case the Version annotation is used.

The following set up inside the client pom (in a multi module project) will provide the maven project version to Nalu:
```xml
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>properties-maven-plugin</artifactId>
        <version>1.0.0</version>
        <executions>
          <execution>
            <goals>
              <goal>set-system-properties</goal>
            </goals>
            <configuration>
              <properties>
                <property>
                  <name>nalu.application.version</name>
                  <value>${project.version}</value>
                </property>
              </properties>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

Not sure, if this is the best approach, but it works. In case you find a more elegant implementation, let me know.

## Debug Annotation (removed in version v2.1.0)
Nalu integrates a log feature that let you trace the routes handled, controllers used, fired events, etc. The debug messages will be displayed using the browser's console.

**This feature is only available during development!**

To activate the log feature, you need to annotate your application class with `@Debug`:
```java_holder_method_tree
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
* **gwt-plugin-gwt**: `DefaultGWTLogger`.class


Nalu integrates a log feature that let you trace the routes handled, controllers used, fired events, etc. The debug messages will be displayed using the browser's console.

**This feature is only available during development!**

To activate the log feature, you need to annotate your application class with `@Debug`:
```java_holder_method_tree
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
* **gwt-plugin-gwt**: `DefaultGWTLogger`.class


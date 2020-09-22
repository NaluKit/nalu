# Multi Module Envirement
Starting with version 2 Nalu improves the multi module support on the client side. In a client side multi module environment it is not possible to share classes without having a common module.

That was the way how client sided mutile modules was implemented in version 1.x using the plugin feature. 

The plugin implementation has two drawbacks:
* It is necessary to have a common module for common events
* It is necessary to have a common context

Because of those two requirements in Nalu v1.x it is necessary to have a common client module. This common module needs to be added to every client module. This will reduce the chance to reuse a client module and increases the complexity.

Regarding j2cl, which will prefer smaller compile units, Nalu needs an improvement.

With version 2, Nalu will help you to avoid a common client module to share the context class and common event classes.

**The plugin feature of Nalu v1.x will be removed in v2.x**

## Creating a module
A sub module does not implement the `IsApplication`-interface. Instead it implements the `IsModule`-interface. This interface needs a `@Module`-annotation. the `@Module`-annotation takes two attributes:

1. **name**: the name of the module.
2. **context**: the contest of the module (see next chapter for more information.

The context is be part of the current module.

Example:
```java
@Module(name = "myModule",
        context = MyApplicationContext.class)
public interface MyModule
    extends IsModule<MyModuleContext> {
}
```

## Context
To share data between modules, it is necessary to extend the `AbstractModuleContext`-class for the main- and the sub-modules.

For more information about the context look [here](xxx).

## Adding a module
To use Nalu sub modules, you need to add the `@Modules`-annotation to the `IsApplication`-interface.

Example:
```java
@Application(startRoute = "/loginShell/login",
             context = MyApplicationContext.class)
@Modules({ MyErrorModule.class,
           MyLoginModule.class })
interface MyApplication
    extends IsApplication {

}
```

## Event
To fire events that are available in all client sided modules, Nalu provides an event class called `NaluApplicationEvent`. This event takes a String, which should be used to describe the event type and accepts a variable numbers of data - which will be stored inside a map. The map is implemented as a `Map<String, Object`. Using the key, you can access the map. The map will always return an `Object` which needs to be casted before using it.

F.e.: If you want to update the selected navigation item in the main module (assuming that the main model will provide navigation) from a client sub module, fire a `NaluApplicationEvent`, set the event string to 'selectNavigationItem' and add the identifier what item inside the navigation is to select as data inside the event store.

The `NaluApplicationEvent`-class uses a builder pattern to at the event name and the data.

To do so, use this code:
```java
this.eventBus.fireEvent(NaluApplicationEvent.create()
                                            .event("selectNavigationItem")
                                            .data("navigationItem", "home"));
```


To catch the event, just add a handler to the event bus. Once catching the event, it is necessary to check if the event is the one you are looking for by comparing the event name with the name of the event you want to catch!
```java
this.eventBus.adddHandler(NaluApplicationEvent.TYPE,
                          e -> {
                            if ("selectNavigationItem".equals(e.getEvent())) {
                              String selectedItem = (String) e.getData("navigationItem");
                              // do something ... 
                            }
                          });
```
**Note: Keep in mind, that you have to cast the stored object to the right type before using it.**

**Important Note: When working with `NaluApplicationEvent`-class, you need to check the event type before handling the event, cause this event will be used for all events!**

















## Multi Module Implementation
Inside a multi module application you need a different implementation of the context. (See the Module wiki page for more information). One motivation for the choosen implementation is to avoid a common project which contains the context class. You can reuse the context class, but you do not have to. This will improve the reuse of child modules in Nalu.

In a multi module application you need to define a context, taht extends the 'AbstractModuleContext'-class.

### MainContext
To define a 'MainContext' for the root application you need to extend the `AbstractMainContext`-class. The `AbstractMainContext`-class uses a data store (implemented as Map) to store the application data.

Here an example of a multi module context:
To access the data store, the `AbstractMainContext`-class provides a `getContext`-method. The above example of the implementation of a single module context will look like that:
```Java
public class MyApplicationContext
  extends AbstractMainContext {
  
  private final static String ATTRIBUTE_KEY = "attribute";
  
  /* only visible inside the main module */
  private MyDataObject myDataObject;
  
  public MyApplicationContext() {
  }
  
  public String getAttribute() {
    return (String) this.getContext().get(MyApplicationContext.ATTRIBUTE_KEY);
  }
  
  public void setAttribute(String attribute) {
    this.getContext().put(MyApplicationContext.ATTRIBUTE_KEY, attribute);
  }
  
  public MyDataObject getMyDataObject() {
    return this.myDataObject;
  }
  
  public void setMyDataObject(MyDataObject myDataObject) {
    this.myDataObject = myDataObject;
  }
}
```
Of course you can save the code for the getter- and setter-methods and access directly the map:
```java_holder_method_tree
String attribute = (String) myApplicationContext.getContext().get(MyApplicationContext.ATTRIBUTE_KEY);
```
every where in your module, but in this case you need to do a cast every time you access the variable!

### Note
This implementation might look a little bit boiler-plated, but it helps you to avoid a common project where all modules and the main module depend on!

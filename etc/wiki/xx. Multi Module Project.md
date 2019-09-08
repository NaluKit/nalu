



#TBD








# Multi Module Envirement
Starting with version 2 Nalu improves the multi module support on the client side. In a client side multi module environment it is not possible to shared classes without having a common module.

That was the way how client sided mutile modules was implementated in version 1.x using the plugin feature. 

The plugin implementation has two drawbacks:
* It is necessary to have a common module for common events
* It is necessary to have a common context

Because of those two requirements in Nalu v1.x it is necessary to have a common client module. This common module needs to be added to every client module. This will reduce the chance to reuse a client module and icreases the complexity.

Regarding j2cl, which will prefer smaller compile units, Nalu needs an improvement.

With version 2, Nalu will help you to avoid a common client module to share the context class and common event classes.

**The plugin feature of Nalu v1.x will be removed in v2.x**

## Creating a module
A module is marked with the `@Module`-annotation. It takes a name and the module context class as attributes. The context should be part of the current module.



## Adding a module
A module is now marked wirh the `@Module`-annotation. It takes a name and the module context class as parameter.

## Context

## Event
To fire events that are available in all client sided modules, Nalu provides an event class called `NaluApplicationEvent`. This event takes a String, which should be used to describe the event type and accepts a variable numbers of data - which will be stored inside a map. The map is implemented as a `Map<String, Object`. Using the key, you can access the map. The map will always return an `Object` which needs to be casted before using it.

F.e.: If you want to update the selected navigation item in the main module (assuming that the main model will provide navigation) from a client sub module, fire a `NaluApplicationEvent`, set the event string to 'selectNavigationItem' and add the identifier what item inside the navigation is to select as data insiede the event store.

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



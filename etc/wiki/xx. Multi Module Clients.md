# Multi Module Client
Starting with version 2 Nalu improves the multi module support on the client side. In a client side multi module environment it is not possible to shared classes without having a common module. That was the way how client sided mutile modules was implementated in version 1.x using he plugin feature. 

The plugin implementation has two drawbacks:
* It is necessary to have a common module for common events
* It is necessary to have a common context

Because of those two requirements in Nalu v1.x it is necessary to have a common client module. This common client module needs to be added to every client module. This will reduce the chance to reuse a client module.

Regarding j2cl, which will prefer smaller compile units, Nalu needs an improvement.

With v2, Nalu will avoid using a common client module to share the context class and common event classes. 




## Creating a module
A module is now marked wirh the `@Module`-annotation. It takes a name and the module context class as parameter.

## adding a module
A module is now marked wirh the `@Module`-annotation. It takes a name and the module context class as parameter.

## Context

## Event
To fire events that are available in all client sided modules, Nalu provides an event class called `NaluEvent`. This event takes as String, which should be used to describe the event type and accepts a variable numbers of data - which will be stored inside a map.

F.e.: If you want to update the selected navigation item, you can fire a `NaluEvent`, set the event string to 'selectNavigationItem' and add the identifier what item inside the navigation is to select as data insiede the event store.

To do so, use this code:
```java
this.eventBus.fireEvent(NaluEvent.create()
                                 .event("selectNavigationItem")
                                 .data("navigationItem", "home"));
```

To catch the event, just add a handler to the event bus:
```java
this.eventBus.adddHandler(NaluEvent.TYPE,
                          e -> {
                            if ("selectNavigationItem".equals(e.getEvent())) {
                              String selectedItem = (String) e.getData("navigationItem");
                              // do something ... 
                            }
                          });
```


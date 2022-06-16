# Motivation & Concepts
Based on a discussion at Gitter the idea of creating Nalu was developed.

The main goal was to create a tiny and simple to use application framework with the following characteristics:

* Full support of the browser's back- forward- and reload-button.
* An optional loader that will be executed at application start to load data from the server.
* A client side context, router and event bus which will be automatically injected in every controller. (Handler have only access to the context and the event bus)
* Filters to intercept routing.
* Parameter Constrains 
* History Support by default.
* Separation of views into a controller and a component with framework sided instantiation.
* A controller life-cycle using `start`-, `mayStop`- and `stop`- similar to GWT Activities & Places.
* Supports HTML links and programmatically routing thanks to a router.
* Controller based handler manager, that will remove all handlers from the event bus in case the controller is stopped (handler registrations must be added to the manager).
* UiBinder-Support (nalu-plugin-gwt)
* Composites to support smaller units
* Controller & component caching
* Component creation inside a controller to support GWT replacement rules and static factory methods
* Multi Shell Support
* Tracking Support
* Multi Module Support

Keeping these requirements in mind the implementation of Nalu was started.

## Basic Idea
The basic idea of Nalu is to use a String to route between screens. In the context of a web application this is done using a hash which gets added to the url. Because of the fact that every controller in Nalu is related to a route, Nalu is able to identify controllers, create the controllers and add the controllers to the DOM.

Parameters are also added to the route, so there is no need to create a place (like it is necessary in GWT's Activities & Places). In case Nalu identifies a controller, it will inject the parameters (which are defined by the route definition inside the `@Controller`-annotation) into the controller.

Nalu supports the usage of multiple shells. This is done by adding the shell's name as first part to the route. In case the shell changes, Nalu will look for a shell defined with this name, remove the old one and add the new.

## Comparison: GWT Activities & Places versus Nalu

Let's compare GWT Activities & places with Nalu and see, what are the key differences:


| Feature                  | GWT |             Nalu             | 
|--------------------------|:---:|:----------------------------:|
| J2CL-Ready               | Yes |             Yes              | 
| MVP-Pattern              | Yes |     [Yes](#MVP-Pattern)      | 
| Composite-Support        | No  |      [Yes](#Composite)       | 
| Handler-Support          | No  |       [Yes](#Handler)        | 
| Block-Controller-Support | No  |   [Yes](#Block-Controller)   | 
| PopUp-Controller-Support | No  |   [Yes](#PopUp-Controller)   | 
| History-Management       | Yes |  [Yes](#History-Management)  |
| Client-Factory           | Yes |    [Yes](#Client-Factory)    | 
| Filter                   | No  |        [Yes](#Filter)        | 
| Component Caching        | No  |  [Yes](#Component-Caching)   |
| Multi-Shell-Support      | No  | [Yes](#Multi-Shell-Support)  |
| Parameter Constrains     | No  | [Yes](#Parameter-Constrains) | 
| Tracker                  | No  |       [Yes](#Tracker)        | 
| Multi Module Support     | No  | [Yes](#Multi-Module-Support) | 

### MVP-Pattern
All visual components in Nalu are implementing the MVP-Pattern. The equivalent of the GWT Presenter is in Nalu a **controller** and and the GWT View is the **component**.

You will find this pattern for:
* [Component Controller](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#controller)
* [Composite Controller](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#composite)
* [Block Controller](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#blockcontroller-since-v200)
* [PopUp Controller](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#popupcontroller-since-v122)

Nalu controller & components have a similar life cycle like GWT Activities. Nalu and GWT both support a `start`-, `mayStop`- amd `stop`-method.

Nalu also support [component caching](#Component-Caching).

### Composite
In Nalu you can devide a visual component into smaller components. This smaller pieces are called **Composites**. Composites have a controller and a component - similar to components, but can not be attached to route. 
Composites can only be used inside components. 

The benefit of composites are:
* composites can be reused
* composites can be used to cache parts of a component
* divide visual components into smaller units of work to improve maintaining.

See: [Composite Documentation](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#composite) for more information.

GWT Activities & Places does not support composite creation.

### Handler
Nalu supports Handler. Handler are controllers without a visual component. Nalu will create the handlers at application start. Handlers are listening for events to get triggered.

See: [Handler Documentation](https://github.com/NaluKit/nalu/wiki/13.-Handler) for more information.

GWT does not support handlers.

### Block-Controller
A block component in Nalu is a always visual component. 

See: [Block Documentation](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#blockcontroller-since-v200) for more information.

GWT Activities & Places does not support block components.

### PopUp-Controller
Nalu supports popups. Popups in Nalu have also controllers and components and get triggered using a special Nalu event.

See: [Popup Documentation](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#popupcontroller-since-v122) for more information.

GWT Activities & Places does not support handlers.

### History-Management
GWT and Nalu both support history management. The back-, forward- and reload-button is supported.

Using [GWT](https://www.gwtproject.org/doc/latest/DevGuideMvpActivitiesAndPlaces.html) you have to create an Activity for each screen and a place containing the Tokenizer. Implementing it, requires the implementation of an Ativity and a Place.

Nalu supports history management by default. No need to implement anything. In Nalu, you don't have Activities nor Places. Nalu uses **routes** to manage history and to navigate. A route in Nalu has all informationen needed to restore a place. The route contains all needed keys and the router [inject the keys](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#parameters) into the controller. All you need to do is to implement a method, that accept a String and is annotated it with @AcceptParameter. See: [**Routes**](https://github.com/NaluKit/nalu/wiki/05.-Routes) for more information.

### Client-Factory
In GWT it is a good advice to have a ClientFactory. The advantage of a ClientFactory is to improve DI and to enable different implementation. 

That's something you do not need to worry about in Nalu. The key components of a Nalu application:

* [Router](https://github.com/NaluKit/nalu/wiki/11.-Router) to manage navigation and history
* [Event Bus](https://github.com/NaluKit/nalu/wiki/08.-Eventbus) to handle events
* [Context](https://github.com/NaluKit/nalu/wiki/10.-Context) client sided session store

are automatically injected into every controller, handler, filter, tracker etc. 

GWT Activities does not support DI. It needs additional implementations.

### Filter
Nalu supports Filter to intercept a routing. Once a routing (navigation to a new screen) occurs, Nalu will use the filter to check if the routing can be done or not. 

GWT Activities does not support filtering out of the box.

### Component-Caching
Nalu supports [component](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#caching) and [composite](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#caching) cahching. 

This means, you can reuse components and composites. 

GWT Activities & Places does not have component caching.

### Multi-Shell-Support
GWT Activities and Places support one visual area where the dynamic components of an application will be presented. If you want to change the visual area or divide it into more areas where compenents should be presented, it gets very difficult.

Nalu supports several shells and a shell in Nalu can have more than one visual area. See [Shell](https://github.com/NaluKit/nalu/wiki/09.-Shell) for more information.

GWT Activities & Places does not have multi shell support. 

### Parameter-Constrains
Nalu supports parameter constrains for parameters inside a route. You can use parameter white- or balck-listung or use regualr expressions to check a parameter. Nalu will validate the parameter against the constraint before injecting it into the controller.

See: [Controller Documentation](https://github.com/NaluKit/nalu/wiki/14.-Controllers-&-Composites#parameters) for more information.

GWT does not support parameter constraints.

### Tracker
Nalu will call a tracker anytime a routing occurs. This might be helpful in case you want to do something in case a routing occurs.

See [Tracking](https://github.com/NaluKit/nalu/wiki/16.-Tracking) for more information.

### Multi-Module-Support
Nalu supports the creation of modules. A Nalu module is a separate Maven module. The classes of a module are invisible to other modules. So, you can create domain specific modules to organize your application.
Another advantage of a module is, once you are working with J2CL, smaller units get transpiled faster.

See [Multi Module Support](https://github.com/NaluKit/nalu/wiki/15.-Multi-Module-Support) for more information.

GWT Activities & Plaes does not support modules.

### Conclusion
Nalu is a J2CL ready replacement for GWT Activites & Places with less code to write and more features. 

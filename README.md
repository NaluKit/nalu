# Nalu
Please use the mvp4g Gitter room for communication.

## Installation
To run the examples:

* clone the repository

* run ```mvn clean install``` to install nalu locally


## Parts of the framework (short summary)

### Application
The ```Application```-class is the entry point of the application. It's the only reference to GWT 2.8.2.

### Application Interface
The application interface must extend ```IsApplication``` and be annotated with ````Application````

### Context
The context is a class that will be injected in every handler and controller. This is a good place to store general needed data.

### Component
The component contains the visible part. It will be autmatically created by the framework and injected to the controller.

### Controller
The controller controls a part of the application. It has to extend ```AbstractComponentController``` and must be annotated with ```@controller```. The component is created by the framework and injected into the controller. The use of the component class respects the view delegate pattern.

A controller annotation looks like this:
```
@Controller(route = "/", selector = Selectors.NAVIGATION, componentInterface = INavigationComponent.class, component = NavigationComponent.class)
```
* The route parameter defines the route, which will make the component visible.
* The selector defines the place inside the DOM where the component will be added.
* The componentInterface is the reference to the componetn interface
* The component is the reference of the component

A route '/' inidicates, that the controller will be executed at appilication start.

#### Parameters
To use parameters, you have to add them to the route:
```
@Controller(route = "/detail/:id", selector = Selectors.CONTENT, componentInterface = IDetailComponent.class, component = DetailComponent.class)
```
To enable parameters, just add: /:parameterName to the route. This requires, that the controller implements a method called: ```setParameterName(String value)```. You can add as much parameters as you like. Every parameter has to Start with '/:'.

### Controller Lifecycle
Every time a routing happens, a new controller will be created and the ```start```-method is called.
Before the component will be removed from the DOM, the ```mayStop```-method is called. This enables the application to interupt the routing.
If a routing occurs, the stop()-method is called. (I hope ... :-))

### Eventbus
Every handler and controller gets a event bus inejcted. So you can easily fire events and listen to them.

### Filter
A filter is triggered by a routing and will executed before the routing occurs. It can stop a navigation and redirect to another page. (f.e.: LogonFilter)

### Handler
Handler are classed (created by the framework at start) that can be used to deal with data, validate, etx. Usually handlers will catch events to get triggered.

### Loader (optional)
A Loader is a class that will be executed at start anables the application to load something from the server.

### Router
The router is injected into the controllers. It enables routing inside the application. Navigation is done by calling:
```
router.route("newRoute", [parameters]);
```

### Shell
iIs the root view, which will be placed into the browser window viewport. The Dom shoudl contain selectors (id) where the childs will be added.




## Notes
It's a proof of concept. Please, keep in mind, validation and tests are missing and there might be bugs in the framework I did not discover yet.

## Known issues

* Recompile does not work due to a bug.

* The debugging feature is not implemented yet.

* documentation missing

* validation!

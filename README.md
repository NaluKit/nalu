# Nalu
Nalu is a tiny framework that helps you to create GWT based applications quite easily.

Nalu is using the HTML 5 history for routing and navigation. This means Nalu supports the browser's back- forward and reload-button by default and without any need to implement something.


Nalu offers the following features:

* Fully support of the browser's back- forward- and relaod-button.

* An optional loader that will be executed at application start to load data from the server.

* A client side context, router and event bus which will be automatically injected in every controller. (Handler have only access to the context and the event bus)

* Filters to intercept routing.

* Full history support.

* Seperation of views into a controller and a component with framwork sided instantiation.

* a controller life-cycle using ```start```-, ```mayStop```- and ```stop```- similar to GWT Activities

* Supports HTML links and programmatically routing thanks to a router.


## Using
To use Nalu, clone the repo and run ```maven clean install``` (ToDo: move to macven central) and add the following dependencies to your pom:

```XML
<dependency>
    <groupId>comgithub..mvp4g</groupId>
    <artifactId>nalu</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>comgithub..mvp4g</groupId>
    <artifactId>nalu-processor</artifactId>
    <version>LATEST</version>
</dependency>
```

See the wiki for more informations on Nalu and how to use it.


## J2CL / GWT3
With the next version of GWT (GWT 3) and the new J2CL transpiller, there will be major changes in the GWT developmemt. For example: JSNI and generators, besides other things, will be gone. To be prepared for the futere things like JSNI, generators or any other dependency to GTW has to be avoided. Nalu uses only the already migrated ```gwt-events``` from ```org.gwtproject```.

Nalu has **no** dependency to gwt-user nor Nalu's dependencies! Nalu does not use JSNI, generators or anything else from GWT. Nalu is ready to use with J2CL / GWT 3.

## To get in touch with the developer
Please use the mvp4g [MVP4G Gitter room](https://gitter.im/mvp4g/mvp4g).



















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
The component contains the visible part. It will be autmatically created by the framework and injected to the controller. It has to implement the ```render```-method, which return an element.

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

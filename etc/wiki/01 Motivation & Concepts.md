# Motivation & Concepts
Based on a discussion at Gitter the idea of creating Nalu was developed.

The main goal was to create a tiny and simple to use application framework with the following characteristics:

* Full support of the browser's back- forward- and reload-button.

* An optional loader that will be executed at application start to load data from the server.

* A client side context, router and event bus which will be automatically injected in every controller. (Handler have only access to the context and the event bus)

* Filters to intercept routing.

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

Keeping these requirements in mind the implementation of Nalu was started.

## Basic Idea
The basic idea of Nalu is to use a String to route between screens. In the context of a web application this is done using a hash which gets added to the url. Because of the fact that every controller in Nalu is related to a route, Nalu is able to identify controllers, create the controllers and add the controllers to the DOM.

Parameters are also added to the route, so there is no need to create a place (like it is necessary in GWT's Activities & Places). In case Nalu identifies a controller, it will inject the parameters (which are defined by the route definition inside the `@Controller`-annotation) into the controller.

Nalu supports the usage of multiple shells. This is done by adding the shell's name as first part to the route. In case the shell changes, Nalu will look for a shell defined with this name, remove the old one and add the new.

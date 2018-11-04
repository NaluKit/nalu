# Nalu

[![Join the chat at https://gitter.im/Nalukit42/Lobby](https://badges.gitter.im/Nalukit42/Lobby.svg)](https://gitter.im/Nalukit42/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.com/NaluKit/nalu.svg?branch=master)](https://travis-ci.com/NaluKit/nalu)

Nalu is a tiny framework that helps you to create GWT based applications quite easily. Nalu is using the HTML 5 history for routing and navigation. This means Nalu supports the browser's back-, forward-, and reload-button by default and without any need to implement something.


Nalu offers the following features:

* Full support of the browser's back- forward- and reload-button.

* An optional loader that will be executed at application start to load data from the server.

* A client side context, router and event bus which will be automatically injected in every controller. (Handler have only access to the context and the event bus)

* Filters to intercept routing.

* History Support by default.

* Seperation of views into a controller and a component with framework sided instantiation.

* A controller life-cycle using ```start```-, ```mayStop```- and ```stop```- similar to GWT Activities.

* Supports HTML links and programmatically routing thanks to a router.

* Controller based handler manager, that will remove all handlers from the event bus in case the controller is stopped to prevent memory leaks (handler registrations must be added to the manager).

* Support for UiBinder (nalu-plugin-gwt)

* Nalu composites to support smaller classes

* Controller & component caching

* Component creation inside a controller to support GWT replacement rules and static factory methods

* Multi Shell Support


## Basic Concept
Nalu uses the hash of an url to navigate.

Example hash:
```
#[shell]/[route]/:[parameter_1]/:[parameter_2]/:[parameter_3]
```

where
* shell: the shell to be used to display the route
* route: is the navigation end point
* parameter_x: are the parameters of the route (it is possible to have a route without parameter or to use a route, that excepts parameter without parameter in inside the url.)

The following flow shows the steps to be done, once a routing is initiated. The flow will end with appending the new component to the DOM.

![Route Flow](https://github.com/NaluKit/nalu/blob/master/etc/images/routeFlow.png)

To connect a component to a route, just create a controller class which extends ```AbstractComponentController```and add the controller annotation ```@Controller```.

```JAVA
@Controller(route = "/shell/route/:parameter_1/:parameter_2/:parameter_3",
            selector = "content",
            component = MyComponent.class,
            componentInterface = IMyComponent.class)
public class MyController
    extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
    implements ISearchComponent.Controller {
}
```

To navigate to a new route use:
```JAVA
    this.router.route("/shell/route",
                      parameter_1,
                      parameter_2);
```
inside the controller.
The Router is automatically injected in the controller. To route to a new component call the route method and add at least the new route. If the route has parameters, just add them as additional parameters. (**Important:** Parameters must be Strings!)

## Using
To use Nalu, clone the repo and run ```maven clean install``` and add the following dependencies to your pom:

```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>com.githubnalukit</groupId>
    <artifactId>nalu-processor</artifactId>
    <version>LATEST</version>
</dependency>
```

Depending on the widget set the project is using, add one of the following plugins:

If the project uses a widget set based on Elemental2, Elemento or Domino-UI, use the **Nalu-Plugin-Elemental2** by adding the following lines to your pom:

```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemental2</artifactId>
    <version>LATEST</version>
</dependency>
```

If the project uses a widget set based on GWT 2.8.2 or newer, use the **Nalu-Plugin-GWT** by adding the following lines to your pom:

```XML
    <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt</artifactId>
      <version>LATEST</version>
    </dependency>
    <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt-processor</artifactId>
      <version>LATEST</version>
    </dependency>
```


See the wiki for more informations on Nalu and how to use it.

## Wiki
More useful information about Nalu and how to use it, can be find inside the [Wiki](https://github.com/nalukit/nalu/wiki).

## J2CL / GWT3
With the next version of GWT (GWT 3) and the new J2CL transpiler, there will be major changes in the GWT developmemt. For example: JSNI and generators, besides other things, will be gone. To be prepared for the future things like JSNI, generators or any other dependency to GWT has to be avoided. Nalu uses only the already migrated ```gwt-events``` from ```org.gwtproject```. (As long as gwt-events is not on Mavan Central the sources of gwt-events will be part of Nalu.)

Nalu has **no** dependency to gwt-user nor Nalu's dependencies! Nalu does not use JSNI, generators or anything else from GWT. Nalu is ready to use with J2CL / GWT 3.

## To get in touch with the developer
Please use the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Examples
There are some examples that show how to set up and how to use Nalu: [https://github.com/nalukit/nalu-examples](https://github.com/nalukit/nalu-examples).

## Project Generator
To speed up creating a Nalu project, the [Nalu Boot Starter Project Generator](http://www.mvp4g.org/gwt-boot-starter-nalu/GwtBootStarterNalu.html) (which is also based on Nalu) can be used. The project generator will generate a Maven project, which can be imported to your preferred IDE and is ready to use. Run **mvn: devmode:** to start the generated project.

Here are some notes about the project generator: [Nalu Project Generator](https://github.com/nalukit/gwt-boot-starter-nalu).



## Notes
Nalu is still in progress. Validation and documentation are not finished yet. In case you find a bug, please open an issue or post it inside the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

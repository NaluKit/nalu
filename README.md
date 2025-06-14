# Nalu

[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg) 
[![Join the chat at https://gitter.im/Nalukit42/Lobby](https://badges.gitter.im/Nalukit42/Lobby.svg)](https://gitter.im/Nalukit42/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.nalukit/nalu.svg?colorB=44cc11)](https://central.sonatype.com/artifact/com.github.nalukit/nalu)
[![Build & Deploy](https://github.com/NaluKit/nalu/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/NaluKit/nalu/actions/workflows/build.yaml)


Nalu is a tiny framework that helps you to create GWT based applications quite easily. Using the HTML 5 history for routing and navigation, Nalu supports the browser's back-, forward-, and reload-button by default and without any need to implement anything.

Nalu supports:

* Route based navigation

* HTML links and programmatically routing thanks to a router

* Browser's back-, forward- and reload-button (In case the hasHistory-attribute of the `Application`-annotation is set to **true**)

* An optional loader that will be executed at application start to load data from the server

* Client side context, router and event bus which will be automatically injected in every controller, filter, loader and handler (Handlers have only access to the context and the event bus)

* Filters to intercept routing.

* Separation of views into controllers and components with framework sided injection - similar to GWT Activities.

* A controller life-cycle using `start`-, `mayStop`- and `stop`- similar to GWT Activities.

* Popups

* Controller based handler manager, that will remove all handlers from the event bus in case the controller is stopped to prevent memory leaks (handler registrations must be added to the manager).

* UiBinder (nalu-plugin-gwt)

* Composites to support smaller units of work

* Controller & composite caching

* Component creation inside a controller to support GWT replacement rules and static factory methods

* Multi Shell Support

* (nearly) Every widget lib (tested with: GWT, GXT, Domino-UI v1 & v2, Elemento, Elemental2, GWT-Material)

* Maven multi module projects to separate an application in smaller parts (module feature)

* Tracking user routing (in case a tracker is added)

## Basic Concept
Nalu uses a hash to navigate. **Starting with version 1.1.0 Nalu supports the use of hash less URLs.** Everything explained here will also work without hash. In case of working with a hash less URL, you need to implement something on the server to handle a reload.

Example hash:
```
#[shell]/[route]/:[parameter_1]/:[parameter_2]
```

where
* shell: the shell to be used to display the route
* route: navigation end point (a controller)
* parameter_x: parameters of the route

The following flow shows the steps, once a routing is initiated. The flow will end with appending the new component to the DOM.

![Route Flow](https://github.com/NaluKit/nalu/blob/main/etc/images/routeFlow.png)

To connect a component to a route, just create a controller class which extend
`AbstractComponentController` and add the controller annotation `@Controller`.
```JAVA
@Controller(route = "/shell/route/{parameter_01}/{parameter_02}",
            selector = "content",
            component = MyComponent.class,
            componentInterface = IMyComponent.class)
public class MyController
    extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
    implements ISearchComponent.Controller {
    
  @AcceptParameter("parameter_01")
  public void setParaemter01(String p01)
      throws RoutingInterceptionException {
      ...
  }
    
  @AcceptParameter("parameter_02")
  public void setParaemter02(String p02)
      throws RoutingInterceptionException {
      ...
  }


}
```
To navigate to a new route use inside a controller:
```JAVA
    this.router.route("/shell/route/{parameter_01}/{parameter_02}",
                      parameter_1,
                      parameter_2);
```
The router is injected in the controller. To route to a new component call the route method and add at least the new route. In case the route has parameters, just add them as additional parameters. (**Important note:** parameters are always Strings!)

## Using

### Dependencies
To use Nalu add the following dependencies to your pom:

* **GWT 2.8.2**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu</artifactId>
    <version>2.10.1-gwt-2.8.2</version>
</dependency>
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-processor</artifactId>
    <version>2.10.1-gwt-2.8.2</version>
    <scope>provided</scope>
</dependency>
```
* **GWT 2.9.0 (and newer) - SNAPSHOT**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu</artifactId>
    <version>HEAD-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-processor</artifactId>
    <version>HEAD-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```
* **GWT 2.9.0 (and newer)  - Release**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu</artifactId>
    <version>2.20.6</version>
</dependency>
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-processor</artifactId>
    <version>2.20.6</version>
    <scope>provided</scope>
</dependency>
```

Depending on the widget set your project is using, add one of the following plugins:

If the project uses a widget set based on **Elemental2**, **Elemento** or **Domino-UI**, use the **Nalu-Plugin-Elemental2** by adding the following lines to your pom:

* **GWT 2.8.2**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemental2</artifactId>
    <version>2.10.1-gwt-2.8.2</version>
</dependency>
```

* **GWT 2.9.0 (and newer)  - SNAPSHOT**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemental2</artifactId>
    <version>HEAD-SNAPSHOT</version>
</dependency>
```

* **GWT 2.9.0 (and newer)  - Release**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemental2</artifactId>
    <version>2.20.6</version>
</dependency>
```

For Elemento there's a dedicated plugin which supports `org.jboss.gwt.elemento.core.IsElement<E>` as widget type:

* **GWT 2.8.2**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemento</artifactId>
    <version>2.10.1-gwt-2.8.2</version>
</dependency>
```

* **GWT 2.9.0 (and newer)  - SNAPSHOT**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemento</artifactId>
    <version>HEAD-SNAPSHOT</version>
</dependency>
```

* **GWT 2.9.0 (and newer)  - Release**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemento</artifactId>
    <version>2.20.6</version>
</dependency>
```

For Domino-UI Version 2 there's also a dedicated plugin which supports `org.dominokit.domino.ui.IsElement` as widget type:

* **GWT 2.9.0 (and newer)  - SNAPSHOT**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-domino-v2</artifactId>
    <version>HEAD-SNAPSHOT</version>
</dependency>
```

* **GWT 2.9.0 (and newer)  - Release**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-domino-v2</artifactId>
    <version>2.20.6</version>
</dependency>
```

**(These plugins are ready to use with J2CL / GWT 3)**

If your project uses a widget set based on **GWT** 2.8.2 or newer, use the **Nalu-Plugin-GWT** by adding the following lines to your pom:

* **GWT 2.8.2**
```XML
   <dependency>
     <groupId>com.github.nalukit</groupId>
     <artifactId>nalu-plugin-gwt</artifactId>
     <version>2.10.1-gwt-2.8.2</version>
   </dependency>
   <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt-processor</artifactId>
      <version>2.10.1-gwt-2.8.2</version>
      <scope>provided</scope>
   </dependency>
```

* **GWT 2.9.0 (and newer)  - SNAPSHOT**
```XML
   <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt</artifactId>
      <version>HEAD-SNAPSHOT</version>
   </dependency>
   <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt-processor</artifactId>
      <version>HEAD-SNAPSHOT</version>
      <scope>provided</scope>
   </dependency>
```

* **GWT 2.9.0 (and newer)  - Release**
```XML
   <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt</artifactId>
      <version>2.20.6</version>
   </dependency>
   <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt-processor</artifactId>
      <version>2.20.6</version>
      <scope>provided</scope>
   </dependency>
```

**(This plugin will not work with J2CL / GWT 3)**

See the wiki for more information about Nalu and how to use it.

### Configuration

Due to a bug in older version of the maven-compiler-plugin, the output of a annotation processor - during a Maven build - will not be logged.
To ensure, that the processor output is logged, use the latest maven-compiler-plugin.

### System Requirements

Nalu supports the latest version of GWT and requires at least Java 11. During a build, Nalu is tested against Java 11, 17 and 21.

## Wiki
More useful information about Nalu and how to use it, can be found inside the [Wiki](https://github.com/nalukit/nalu/wiki).

## J2CL / GWT3
With the next version of GWT (GWT 3) and the new J2CL transpiler, there will be major changes in the GWT development. For example: JSNI and generators, besides other things, will be gone. To be prepared for the future things like JSNI, generators or any other dependency to GWT has to be removed and must be avoided.

Nalu uses only the already migrated `gwt-events` from `org.gwtproject`.
 
Nalu has **no** dependency to gwt-user nor Nalu's dependencies! Nalu does not use JSNI, generators or anything else from GWT (except the nalu-plugin-gwt) which will block moving to J2CL.

Nalu is ready to use with J2CL / GWT 3 as long as you do not use the nalu-plugin-gwt!

## To get in touch with the developer
Please visit use us at [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Examples
Here you will find many examples that show Nalu in action: [https://github.com/nalukit/nalu-examples](https://github.com/nalukit/nalu-examples).

## YouTube
You will find a session about Nalu at the GWT Community Channel [here](https://www.youtube.com/watch?v=GsHY4f3cvRU).

## Notes
In case you find a bug, please open an issue or post it inside the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Comparsion between GWT Activities & Places versus Nalu
Anyone, who is familiar with GWT Activities & Places, will find [here](https://github.com/NaluKit/nalu/wiki/01.-Motivation-&-Concepts#comparison-gwt-activities--places-versus-nalu) a comparison between GWT Activities & Places and Nalu.


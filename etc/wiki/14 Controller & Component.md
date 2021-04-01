# Controllers and Composites
Nalu mainly helps you to define three types of elements:

* Handler (a class, without visual components see [Handler](https://github.com/NaluKit/nalu/wiki/13.-Handler))
* Controller
* Components

This page will show you how to build Controllers and Components.

If you are familiar with the MVP pattern:
In Nalu a controller can be compared with the presenter and the component with the view of the MVP pattern.

Nalu requires to use the view delegate pattern.

## Controller
A visual part in Nalu will always be a combination of a controller and a component. The Controller will load data, fire events, handle events and routes to other controllers. A Controller should never contain classes and interfaces of a widget library.

### Defining a Controller
To create a controller, you have to:

* extend AbstractComponentController<C, V, W>
   - C: type of the context
   - V: type of the view (interface) that will be injected in the controller
   - W: type of the base class of your widget library (for GWT & GXT: Widget, Elemento and Domino-UI: HTMLElement)

By extending AbstractComponentController you will have access to the allowing instances:

* C context: instance of the application context (Singleton)
* V view: instance of the view
* eventBus: instance of the application wide event bus
* router: instance of the router

To tell Nalu, that this class is a controller, you have to annotate the class with `Controller`. Nalu will automatically create an instance for each class annotated with `@Controller` in case the route of the controller gets triggered. (except in cases where the controller is cached.)

A controller requires a

* a public, zero-argument constructor
* annotate the controller with `@Controller`

Here is an example of a controller:

```java_holder_method_tree
@Controller(route = "/shell/route01/route02/:id",
            selector = "content",
            componentInterface = IMyComponent.class,
            component = MyComponent.class)
public class DetailController
 extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
 implements IMyComponent.Controller {


 public MyController() {
 }

 ...

}
```

### Controller annotation
To let Nalu automatically create a controller the controller class (which extend `AbstractComponentController`) needs to be annotated with `@Controller`.

The `@Controller` annotation has four required attributes:

* route: the route which activates the controller. A route has two parts:
   - the first part contains the path to the controller. A route starts always with a '/' and is used to separate routes
   - the second part are the parameters that will be injected in the controller. To define a parameter inside a route, use '/:parameter name'. Parameters are optional
* selector: a selector defines an ID inside the DOM (using the element plugin) or an `add`-method (using the GWT-plugin) which will be called to add a widget to the DOM.
* componentInterface: the type of the interface for your component
* component: the type of the component

The 'componentInterface'-attribute will be used inside the controller as reference of the component interface, where as the 'component'-attribute will be used to instantiate the component. By default Nalu uses `new` to create an instance of a component. (`GWT-create()` will no longer be available in J2CL / GWT 3! And, to be ready for J2CL / GWT 3 Nalu has to avoid using `GWT.create`.).

You can assign a controller to several shells and routes.
This is an example of a controller, that can be used with the shell 'logon' and 'application'. To define this, use: `/[logon|application]/part01/part02/`.

To assign a controller to several routes:
```java
@Controller(route = { "/shell/route01/route02/:id",
                      "/shell/route03/:id/route04",
                      "/shell/route01/route02/route03/:id" },
            selector = "content",
            componentInterface = MultiRouteComponent.class,
            component = MultiRouteComponentImpl.class)
public class MultiRouteController
    extends AbstractComponentController<MultiRouteContext, MultiRouteComponent, HTMLElement>
    implements MultiRouteComponent.Controller {
}
```
Keep in MInd, that all routes need the same number and names for the parameters.

### Parameters
In case a controller accepts parameters, you have to add the parameters to the route attribute. Parameters are defined by using '/:parameterName'.

To let Nalu inject a parameter from the route into the controller, you have to create a method, which accepts a String parameter and annotate the method using the `@AcceptParameter("parameterName")` annotation.

For example, if the controller requires an 'id', the controller class will look like this:

```Java
@Controller(route = "/shell/route01/route02/:id",
                    selector = "content",
                    componentInterface = IMyComponent.class,
                    component = MyComponent.class)
public class MyController
 extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
 implements IMylComponent.Controller {

 private long id;

 public DetailController() {
 }

 @AcceptParameter("id")
 public void setId(String id)
   throws RoutingInterceptionException {
   try {
     this.id = Long.parseLong(id);
   } catch (NumberFormatException e) {
     // in case of an exception route to another page ...
     throw new RoutingInterceptionException(this.getClass()
                                                .getCanonicalName(),
                                            "/person/search",
                                            this.context.getSearchName(),
                                            this.context.getSearchCity());
   }
 }
}
```

**Note:**
At the time parameters are set by Nalu, the component is not rendered! Rendering of the component will occur after all parameters are set!


### Life Cycle
A Nalu controller has a life cycle. Nalu supports the methods:

* `start`: will be called after the component is created. This method will not be called in case a controller is cached.
* `active`: will be called after the `start`-method. This method will be called in case a controller is cached.
* `mayStop`: will be called before a new routing occurs. In case the method return a String and not a null value, Nalu will display the String and abort the routing.
* `deactive`: will be called before the `stop`-method. This method will be called in case a controller is cached.
* `stop`: in case of a successful routing (not interrupted by a filter or the `mayStop` method) this method will be called. This method will not be called in case a controller is cached.

Every time the application gets a new route, Nalu will create a new instance of the controller (and of course of the component). In case a controller is cached Nalu will use the cached instance of the controller.

#### onAttach Method
Nalu will call the `onAttach` method, after the component is added to the DOM. **This is an internal method, that should not be overwritten. Use the `active`-method inside the controller instead.**

#### onDetach Method
Nalu will call the `onDetach` method, after the component is removed to the DOM. **This is an internal method, that should not be overwritten. Use the `deactive`-method inside the controller instead.**


#### bind Method (since v1.2.1)
Nalu will call the `bind` method before the component is created. This is an internal method, that can be overwritten. That's a good place to do some preparing actions. The method is asynchronous. This makes it possible to do a server call before Nalu will continue. To tell Nalu to continue, call `loader.continueLoading()`. In case you want to interrupt the loading, just do not call `loader.continueLoading()` and use the router to route to another place.


#### Event Bus
A controller has access to the application event bus. Nalu uses the classes of the module `org.gwtproject.events`. This artifact is ready for GWT 3/J2CL and works similar like the event bus from GWT. You can add handler to the event bus and fire events. A good place to register a handler on the event bus is the `bind`-method or `start` method.

#### Handler Management
Nalu supports an automatic handler removing.

There are three types of handler registrations managment Nalu supports:

1. handler registrations inside a component (f.e.: ClickHandlers, etc.)
2. handler registrations inside a ComponntController or CompositeController
3. global handler registrations inside a ComponntController or CompositeController

Depending on the type of the handler registration you are using, the handler will be removed at different times.

##### Handler Registrations inside a Component
Inside a component, you can add a `HandlerRegistration` like this:

```java_holder_method_tree
   this.handlerRegistrations.add(this.myWidget.addClickHandler(e -> doSomething()));
```

After the `stop`-method of the associated controller gets called, all added handler registrations will be removed. This behavior is the same for normal components and composite components.

##### Handler Registrations inside a ComponntController or CompositeController
Inside a ComponntController or CompositeController, you can add a `HandlerRegistration` like this:

```java_holder_method_tree
   this.handlerRegistrations.add(this.eventBus.addHandler(MyEvent.TYPE,
                                                          e -> doSomething()));
```

or, in case you want to add more than one:

```java_holder_method_tree
   this.handlerRegistrations.compose(this.eventBus.addHandler(MyEvent01.TYPE,
                                                          e -> doSomething01()),
                                     this.eventBus.addHandler(MyEvent02.TYPE,
                                                          e -> doSomething02()));
```

Place the adding into the `active`-method, because after the `deactive`-method of the associated controller gets called, all added handler registrations will be removed. This behavior is the same for normal components and composite components.

**Note**:

**If you are using cached componets, then the handler will only be active in case the component is visible.**

##### Global Handler Registrations inside a ComponntController or CompositeController
Inside a ComponntController or CompositeController, you can add a `HandlerRegistration` like this:

```java_holder_method_tree
   this.globalHandlerRegistrations.add(this.eventBus.addHandler(MyEvent.TYPE,
                                                                e -> doSomething()));
```

or, in case you want to add more than one:

```java_holder_method_tree
   this.globalHandlerRegistrations.compose(this.eventBus.addHandler(MyEvent01.TYPE,
                                                                    e -> doSomething01()),
                                           this.eventBus.addHandler(MyEvent02.TYPE,
                                                                    e -> doSomething02()));
```

Place the adding into the `start`-method, because after the `stop`-method of the associated controller gets called, all added handler registrations will be removed. This behavior is the same for normal components and composite components.

**Note**:

**If you are using cached componets, then the handler will only be always active!**

#### Component Creation
Nalu will automatically create a component using the Java 'new'-command and inject the component into the controller. Therefore the component needs to have a zero argument constructor.

In some cases this might be a problem.

You can tell Nalu to use a method inside the controller and create the component by your own. To do so, you have to annotate the controller with `IsComponentCreator<V>` and implement a `createComponent`-method.

```java_holder_method_tree
@Controller(...)
public class MyController
  extends AbstractComponentController<MyContext, IMyComponent, HTMLElement>
  implements IMyComponent.Controller,
             IsComponentCreator<IMyComponent> {

  ...


  @Override
  public IMyComponent createComponent() {
    return new MyComponent();
  }
}

```

The `IsComponentCreator`-interface will also work with Nalu's composites!

Note: At the time Nalu calls the `createComponent`-method, the context is already injected.

## Component
A component represents the visual part. This is the place where the layout is created, fields are added, etc.

### Defining a component
To create a component, you have to:

* extend AbstractComponent<C, W>
   - C: type of the controller (defined as interface)
   - W: type of the base class of your widget library (for GWT & GXT: Widget, Elemento and Domino-UI: HTMLElement)

A component requires a

* a public, zero-argument constructor
* setting the element inside the `render` method using `initElement(widget)`

Nalu will, after a component is created, call the `render` method. So the application is able to create the visible parts. Once you are ready with your layout, call the `initElement` method and use the root element as parameter. Once, it is done, Nalu will use the element of the component and add it to the DOM.

It is possible to call the `getController`-method inside the `render`-method to obtain values from the controller.

### Execution Order
The following image shows the execution order in case a new controller is created:

![Execution Order](https://github.com/NaluKit/nalu/raw/main/etc/images/controllerFlow.png)

## Composite
Imagine, you have a view, that looks like that:

![Route Flow](https://github.com/NaluKit/nalu/raw/main/etc/images/view-mock-up.png)

Of course it is possible to render this view inside one component. In some cases, where your controller and component will have to much code or you want to reuse one of the composite01 component, composite02 component and composite03 component, you can extract them in separate controller and component pairs. But this would not be handled but Nalu.

Nalu offers - with the support for Composites - a feature to help you creating such things.

Composites in Nalu are treated like controllers, but can **not** be used by a route. That's the different between a composite and a controller. Composites will have access to the event bus, router and context. A Composite controller behave like a normal controller. Except, that it can be only activated from a controller and not from the router!

### Creating a Composite
To create a composite, you have to:

* extend AbstractCompositeController<C, V, W>
   - C: type of the context
   - V: type of the view (interface) that will be injected in the controller
   - W: type of the base class of your widget library (for GWT & GXT: Widget, Elemento and Domino-UI: HTMLElement)

By extending AbstractCompositeController you will have access to the allowing instances:

* C context: instance of the application context (Singleton)
* V view: instance of the view
* event bus: instance of the application wide event bus
* router: instance of the router

To tell Nalu, that this class is a controller, you have to annotate the class with `CompositeController`. Nalu will automatically create an instance for each class annotated with `@CompositeController`.

A composite requires a

* a public, zero-argument constructor
* annotate the controller with `@CompositeController`

Here is an example of a composite:

```java_holder_method_tree
@@CompositeController(componentInterface = IMyCompositeComponent.class,
                      component = MyCompositeComponent.class)
public class MyComposite
  extends AbstractCompositeController<MyApplicationContext, IMyCompositeComponent, HTMLElement>
  implements IMyCompositeComponent.Controller {

  public MyComposite() {
  }


 }
```

#### CompositeController annotation
To let Nalu automatically create a composite the composite class (which extend `AbstractCompositeController`) needs to be annotated with `@CompositeController`.

The `@CompositeController` annotation has two required attributes:

* componentInterface: the type of the interface for your component
* component: the type of the component

The 'componentInterface'-attribute will be used inside the composite controller as reference of the component interface, where as the 'component'-attribute will be used to instantiate the composite component. By default Nalu uses the `new` to create an instance of an component. (`GWT-create()` will no longer be available in J2CL / GWT 3! And, to be ready for J2CL / GWT 3 Nalu has to avoid using `GWT.create`).

#### Life Cycle
A Nalu composite controller has a life cycle similar the life cycle of a controller. Nalu supports the methods:

* `start`: will be called after the component is created for a controller that is not cached.

* `activate`: this will be called every time a controller gets active independent from the fact, if the controller is newly created or cached.

* `mayStop`: will be called before a new routing occurs. In case the method return a String and not a null value, Nalu will display the String and abort the routing.

* `deactivate`: this will be called every time a controller gets inactive independent of weather the controller is newly created or cached.

* `stop`: in case of a successful routing (not interrupted by a filter or the `mayStop` method) this method will be called. In case the controller is cached the `stop`-method will not be called.

Every time the application composite is required by a controller, Nalu will create a new instance of the composite controller (and of course of the component).

#### onAttach Method
Nalu will call the `onAttach` method, after the component is added to the DOM. **This is an internal method, that should not be overwritten. Use the active-method inside the controller instead.**


#### onDetach Method
Nalu will call the `onDetach` method, after the component is removed to the DOM. **This is an internal method, that should not be overwritten. Use the deactive-method inside the controller instead.**

#### Event Bus
A controller has access to the application event bus. Nalu uses the the events of the module `org.gwtproject.events`. This artifact is ready for GWT 3/J2CL and works similar like the event bus from GWT. You can add handler to the event bus and fire events. A good place to register a handler on the event bus is the  `bind`-method or `start`-method.

#### Handler Management
Nalu supports automatic handler removing.

In case the controller adds a handler to the event bus, add the `HandlerRegistration` to the controller handler registration list. Once the controller is stopped, all handlers will be removed from the event bus. Use the `active`-method to add a handler. Nalu will remove the handlers in case of a deactivation of the controller. So, in case the controller is cached the handlers will be removed and once the cached controller gets active again, the `activate`-method gets called again.

```java_holder_method_tree
@Override
 public void activate() {
   // add the handler registration to the HandlerRegistrations class of this controller
   // Doing that will help that once the controller gets stops all handler registrations
   // will be removed!
   this.handlerRegistrations.add(this.eventBus.addHandler(MyEvent.TYPE,
                                                          e -> doSomething())));
 }
```

### Composite Component
A component represents the visual part. This is the place where the layout is created, fields are added, etc.

#### Defining a Composite Component
A composite component looks like a normal component. To create a composite component, you have to:

* extend AbstractCompositeComponent<C, W>
   - C: type of the composite controller (defined as interface)
   - W: type of the base class of your widget library (for GWT & GXT: Widget, Elemento and Domino-UI: HTMLElement)

A composite component requires

* a public, zero-argument constructor
* setting the element inside the `render` method using `initElement(widget)`

Nalu will, after a composite component is created, call the `render` method. So the application is able to create the visible parts. Once you are ready with your layout, call the `initElement` method and use the root element as parameter. Once, it is done, Nalu will use the element of the component and add it to the DOM.

It is possible to call the `getController`-method inside the `render`-method to obtain values from the controller.

### Adding Composites to a Controller
In order to use composite controllers in Nalu, you have to add the `Composite` annotation to a controller. The `Composites` annotation will have another `Composite` annotation for each composite.

Here an example of how to use composites in a controller. This example shows a controller, which uses two composites:

```java_holder_method_tree
@Controller(route = "/shell/route01/route02/:parameter01",
            selector = "selector01",
            componentInterface = IMyComponent.class,
            component = MyComponent.class)
@Composites({
  @Composite(name = "composite01",
                    compositeController = MyComposite01.class,
                    selector = "composite01"),
  @Composite(name = "composite02",
                    compositeController = MyComposite02.class,
                    selector = "composite02")
})
public class MyCompositeUsingController
 extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
 implements IMyComponent.Controller {

 ...

}
```

Nalu will inject the instance of the created composite controllers into the controller. So, the controller can easily access the composite. To access the instance of a composite, use this statement:

```java_holder_method_tree

 MyComposite01 instanceOfComposite01 = super.<MyComposite01>getComposite("composite01");

```

In some cases, especially if the selector of the composite is defined outside the DOM of the component, the composite component will not be removed if a new routing occurs. In this cases overriding the `remove` method of the `AbstractCompositeComponent` class will help. Just add in this method the code to remove the out most container by calling `myOuterContainer.remove()` or `myOuterContainer.removeFromParent()`. Nalu will call the `remove` method once a composite controller gets stopped.

#### Conditional Composite (since v1.2.2)
Starting with version 1.2.2 Nalu will support conditional composite. A conditional composite is a composite that will be insert into the screen depending on a condition. So, it is possible to decide whether to show or not show a composite depending on the route, parameters or information stored inside the context.

A conditional composite requires a class that extends `AbstractCompositeCondition`. Inside this class the `boolean loadComposite(String route, String... params)` must be implemented. Returning **true** will tell Nalu to create the composite and it to the component. In case the value **false** is returned, Nalu will not create the composite and add it to the component.

The conditional class is another attribute of the `Composite` - annotation called 'condition'.

Here is an example of the usage of a conditional composite.

First, create the condition:
```java_holder_method_tree
public class MyCompositeCondition
    extends AbstractCompositeCondition<MyApplicationContext> {

  @Override
  public boolean loadComposite(String route,
                               String... params) {
    if ([condition]) {
      return false;
    } else {
      return true;
    }
  }

}
```

Use the condition inside the `Composite`-annotation:


```java_holder_method_tree
@Controller(route = "/shell/route01/route02/:parameter01",
            selector = "selector01",
            componentInterface = IMyComponent.class,
            component = MyComponent.class)
@Composites({
  @Composite(name = "composite01",
                    compositeController = MyComposite01.class,
                    selector = "composite01"),
  @Composite(name = "composite02",
                    compositeController = MyComposite02.class,
                    selector = "composite02",
                    condition = MyCompositeCondition.class)
})
public class MyCompositeUsingController
 extends AbstractComponentController<MyApplicationContext, IMyComponent, HTMLElement>
 implements IMyComponent.Controller {

 ...

}
```

Now, depending of the return value of the `loadComposite`-method, the composite will be created and added or not.

**Keep in mind: in case the condition class returns false, Nalu will not create an instance of the composite!**

## BlockController (since v2.0.0)
Starting with v2.0.0 Nalu provides a new controller type: the **BlockController**.

A **BlockController** behaves like a normal controller, but gets created at the start of the application and will be permanent visible.

A BlockController has the following features:

* has no route (and because of that: no history!)
* has no selector (Nalu will ask the BlockController to append the element)
* has no life-cycle
* can not be cached (cause it is cached by default)

### Defining a BlockController
To create a BlockController, you have to:

* extend AbstractBlockComponentController<C, V>
  * C: type of the context
  * V: type of the view (interface) that will be injected in the controller

By extending AbstractBlockComponentController you will have access to the allowing instances:

* C context: instance of the application context (Singleton)
* V view: instance of the view
* event bus: instance of the application wide event bus
* router: instance of the router

To tell Nalu, that this class is a **BlockController**, you have to annotate the class with `@BlockController`. Nalu will automatically create an instance for each class annotated with `@BlockController` the first time the application gets loaded. Once the controller is created, it will always be used.

A controller requires a

* a public, zero-argument constructor
* annotate the controller with `@BlockController`
* extends AbstractBlockComponentController
* 
Here is an example of a controller:

```java_holder_method_tree
@BlockController(name = "fork",
                 componentInterface = IForkBlockComponent.class,
                 component = ForkBlockComponent.class)
public class ForkBlockController
    extends AbstractBlockComponentController<NaluSimpleApplicationContext, IForkBlockComponent>
    implements IForkBlockComponent.Controller {

  public ForkBlockController() {
  }

}
```

### BlockController Annotation

To let Nalu automatically create a **BlockController** the controller class (which extend `AbstractBlockComponentController`) needs to be annotated with `@BlockController`.

The `@BlockController` annotation has three required attributes:

* name: the name used to identify the controller
* componentInterface: the type of the interface for your component
* component: the type of the component

and an additional, optional attribute:

* condition: used by Nalu to hide or show the component

The 'componentInterface'-attribute will be used inside the controller as reference of the component interface, where as the 'component'-attribute will be used to instantiate the component. By default Nalu uses the new to create an instance of an component. (GWT-create() will no longer be available in J2CL / GWT 3! And, to be ready for J2CL / GWT 3 Nalu has to avoid using GWT.create).

You can use the `IsBlocKComponentCreator`-interface to create the component inside your controller.

Nalu will inject:

* the instance of the router
* the instance of the event bus
* the instance of the context
* the instance of the component

into the controller.

### Life Cycle of a BlockController

A Nalu **BlockController** has no life cycle. The controller (and it's component) will be created at the start of the application. Once it is created, Nalu will always use this instance.

Nalu will call several methods inside the **BlockController**:

* onBeforeShow: will be called before the show-method. (A good place to initialize the controller and component)
* show: to show the component.
* onBeforeHide: will be called before the hide-method. (A good place to clean up the controller and component)
* hide: to hide the component.
* append: to add the component to the DOM

It is up to the controller to show, hide and append the component..

### BlockComponent Creation

Nalu will automatically create a component using the Java 'new'-command and inject the component into the controller. Therefore the component needs to have a zero argument constructor.

In some cases this might be a problem.

You can tell Nalu to use a method inside the controller and create the component by your own. To do so, you have to annotate the controller with `IsBlockComponentCreator<V>` and implement a createBlockComponent-method.

```java_holder_method_tree
@BlockController(name = "fork",
                 componentInterface = IForkBlockComponent.class,
                 component = ForkBlockComponent.class)
public class ForkBlockController
    extends AbstractBlockComponentController<NaluSimpleApplicationContext, IForkBlockComponent>
    implements IForkBlockComponent.Controller,
               IsBlockComponentCreator<IForkBlockComponent> {

  public ForkBlockController() {
  }

  ...
  
  @Override
  public IForkBlockComponent createBlockComponent() {
    return new ForkBlockComponent();
  }

}
```

Note: At the time Nalu calls the createBlockComponent-method, the context is already injected.

#### Controlling the Block using Events
Starting with version 2.1.0 Nalu will provide events to control the visibility of a block.

To make a BlockComponent visible, just fire the `ShowBlockComponentEvent` by calling:
```java_holder_method_tree
this.eventBus.fireEvent(ShowBlockComponentEvent.show("MyBlockControllerName"));
```
The `show`-method will take the name of the BlockController to show.

To make a BlockComponent invisible, just fire the `HideBlockComponentEvent`:
```java_holder_method_tree
this.eventBus.fireEvent(HideBlockComponentEvent.hide("MyBlockControllerName"));
```
The `hide`-method will take the name of the BlockController to hide.

Both events accept parameters. To add a parameter to the event, use:
```java_holder_method_tree
this.eventBus.fireEvent(ShowBlockComponentEvent.show("MyBlockControllerName")
                                               .using("myParameter01", "myValue01")
                                               .using("myParameter02", "myValue02"));
```

## PopUpController (since v1.2.2)
Before version 1.2.2 Nalu does not support pop-ups. Starting with version 1.2.2 Nalu gets a new controller type: **PopUpController**. The new controller will enable Nalu to handle pop-ups.

A PopUpController has the following features:

* has no route (and because of that: no history!)
* has no selector (Nalu will not add the component of a PopUpController to the shell, cause it is a pop up!)
* has no life-cycle
* can not be cached (cause it is cached by default)
* is triggered by an event

### Defining a PopUpController
To create a pop-up Controller, you have to:

* extend AbstractPopUpComponentController<C, V>
   - C: type of the context
   - V: type of the view (interface) that will be injected in the controller

By extending AbstractPopUpComponentController you will have access to the allowing instances:

* C context: instance of the application context (Singleton)
* V view: instance of the view
* event bus: instance of the application wide event bus
* router: instance of the router

To tell Nalu, that this class is a pop-up controller, you to annotate the class with `PopUpController`. Nalu will automatically create an instance for each class annotated with `@PopUpController` the first time it is requested. Once the controller is created, it will always be reused.

A controller requires a

* a public, zero-argument constructor
* annotate the controller with `@PopUpController`
* extends `AbstractPopUpComponentController`

Here is an example of a controller:

```java_holder_method_tree
@PopUpController(name = "PopUpEditor",
                 componentInterface = IMyComponent.class,
                 component = MyComponent.class)
public class MyPopUpController
    extends AbstractPopUpComponentController<MyContext, IMyComponent>
    implements IMyComponent.Controller,
               IsPopUpComponentCreator<IDetailComponent> {
 public MyPopUpController() {
 }

 ...

}
```

### PopUpController annotation
To let Nalu automatically create a pop-up controller the controller class (which extend `AbstractPopUpComponentController`) needs to be annotated with `@PopUpController`.

The `@PopUpController` annotation has three required attributes:

* name: the name used to identify the controller
* componentInterface: the type of the interface for your component
* component: the type of the component

The 'componentInterface'-attribute will be used inside the controller as reference of the component interface, where as the 'component'-attribute will be used to instantiate the component. By default Nalu uses the `new` to create an instance of an component. (`GWT-create()` will no longer be available in J2CL / GWT 3! And, to be ready for J2CL / GWT 3 Nalu has to avoid using `GWT.create`).

You can use the `IsPopUpComponentCreator`-interface to create the component inside your controller.

Nalu will inject:

* the instance of the router
* the instance of the event bus
* the instance of the context
* the instance of the component

into the controller.

### Trigger a PopUpController
A PopUpController is not bound to a route. To trigger a PopUpController fire the `ShowPopUpEvent`. The `ShowPopUpEvent` takes the name of the pop-up (will be used to identify the pop-up inside the `PopUpControllerFactory`) and add the necessary parameters. The `name` value is mandatory where as the parameters are optional.

The following code shows how to trigger a PopUpController in Nalu:

```java_holder_method_tree
this.eventBus.fireEvent(ShowPopUpEvent.show("PopUpEditor")
                                      .using("id",
                                             "4711"));
```
The code above triggers the PopUpController using the name 'PopUpEditor' and adds 'id' to the event. (A parameter value must be a String)

Nalu will inject the parameters into the `dataStore` inside the `AbstractPopUpComponentController`-class. To retrieve the value use:

```java_holder_method_tree
String id = super.dataStore.get("id");
```

### Life Cycle
A Nalu pup-up controller has no life cycle. The controller (and it's component) will be created the first time an event is requesting this pop-up. Once it is created, Nalu will always reuse this instance.

Nalu will call three methods inside the `PopUpController`:

1. `bind`: will be called at application start, at the time when the controller is created. At that time, the component is not created and the `component`-instance is null. The method is a good place to add global handler, etc. The method is asynchronous. To give the control back to Nalu, just call: `finishLoadCommand.finishLoading`.
2. `onBeforeShow`: will be called before the `show`-method. (A good place to initialize the controller and component each time before it gets visible) The method is asynchronous. To give the control back to Nalu, just call: `finishLoadCommand.finishLoading`.
3. `show`: to show the controller.

**It is up to the controller to show the pop-up and hide it.**

### Component Creation
Nalu will automatically create a component using the Java 'new'-command and inject the component into the controller. Therefore the component needs to have a zero argument constructor.

In some cases this might be a problem.

You can tell Nalu to use a method inside the controller and create the component by your own. To do so, you have to annotate the controller with `IsPopUpComponentCreator<V>` and implement a `createPopUpComponent`-method.

```java_holder_method_tree
@PopUpController(...)
public class MyController
  extends AbstractPopUpComponentController<MyContext, IMyComponent>
  implements IMyComponent.Controller,
             IsPopUpComponentCreator<IMyComponent> {

  ...


  @Override
  public IMyComponent createPopUpComponent() {
    return new MyComponent();
  }
}

```

Note: At the time Nalu calls the `createPopUpComponent`-method, the context is already injected.

### ShowPopUpEvent-Filtering
Similar to the route filter feature Nalu offers a ShowPopUpEvent-Filter. Any time a `ShowPopUpEvent` is fired all `filter`-methods of the popup filters are executed. In case that at least one of filters returns `false`, the event gets canceled. A popup filter has access to the event. So it is possible to check the name of the requested popup and can access all values.

Creating a PopUpFilter is quite easy:
```java
public class MyPopUpFilter
    extends AbstractPopUpFilter<MyContext> {

  public IolaniEditPopUpFilter() {
  }

  @Override
  public boolean filter(ShowPopUpEvent event) {
    if ("MyDoNotShowPopUp".equals(event.getName())) {
      return false; // never show this popup
    }
    return true; // we are happy to continue
  }

}
```

By using the `PopUpFilter`-annotation, you can register the popup filter:
```java
@Application(context = MyContext.class,
             startRoute = MyRoutes.ROUTE_START)
@PopUpFilters(filterClasses = MyPopUpFilter.class)
public interface IolaniApplication
    extends IsApplication {
}
```

An application can have more than one popup filter.

## Multiple Route Support
A controller in Nalu can be atteched to more than one route. To define a controller for several routes, use a list of Strings for the `route`-attribute.


```java_holder_method_tree
@Controller(route = { "/shell/route01/route02/:id",
                      "/shell/route03/:id/route04",
                      "/shell/route01/route02/route03/:id" },
            selector = "content",
            componentInterface = MultiRouteComponent.class,
            component = MultiRouteComponentImpl.class)
public class MultiRouteController
  extends AbstractComponentController<MultiRouteContext, MultiRouteComponent, HTMLElement>
  implements MultiRouteComponent.Controller {

  private String id;

  public MultiRouteController() {
  }
  
  @AcceptParameter("id")
  public void setId(String s) {
    this.id = (s != null) ? s : "";
  }
}
```

Nalu will validate, that

* all routes have the same number of parameters
* all parameters have the same name
* there is no duplicate route

## Caching
Nalu provides a caching mechanism. This allows to store a controller/component for a route. Next the route will be used, Nalu restores the cached controller/component instead of creating a new controller and component.

To tell Nalu to cache a controller/component, use the `router.storeInCache(this)`-command inside the controller. Now, caching for this route is active. To stop caching, call `router.removeFromCache(this)`.

You can also cache composites.

Since 1.3.2 there is also a possibility to cache a composite as a singleton, so different sites can share a composite with the same state. You can configure this with the attribute scope in the annotation @Composites of the controllers which contain the composites:

```java_holder_method_tree
  @Composites({ @Composite(name = "myComposite",
                           compositeController = MyComposite.class,
                           selector = "my-composite",
                           scope = Scope.GLOBAL) })
  public class MyCompositeContainerController {
    
    ...
  }
```

There are two kind of scopes:

* Scope.GLOBAL to cache the composite as a singleton (share between components / sites)
* Scope.LOCAL to cache the composite only for this component / site (default)

## Reusing Controllers & Components

In case you have a controller/component pair (using a route f.e. like this: `/application/person/detail/:id`) and you want to page through the person data, which means, the only thing that changes is the `id`, you can do this by using the same route and only changing the id. Nalu will always create a new controller/component pair in case of routing.

Starting with version 2.1.0, Nalu will offer a new way to deal with this use case. Instead of always creating new components, you can tell Nalu to reuse them. To do so, just use `super.setMode(Mode.REUSE);`. this is the example code of a controller:

```java_holder_method_tree
@Controller(route = "/application/person/detail/:id",
            selector = "content",
            componentInterface = IDetailComponent.class,
            component = DetailComponent.class)
public class DetailController
    extends AbstractComponentController<Context, IDetailComponent, HTMLElement>
    implements IDetailComponent.Controller,
               IsComponentCreator<IDetailComponent> {
  
  private long id;
  
  public DetailController() {
  }
  
  @Override
  public void start() {
    super.setMode(Mode.REUSE);
  }
  
  @Override
  public void activate() {
    // ToDo: load data, set up component, etc.
  }
  
  @AcceptParameter("id")
  public void setId(String id)
      throws RoutingInterceptionException {
    try {
      this.id = Long.parseLong(id);
    } catch (NumberFormatException e) {
      // ToDo: error handling 
    }
  }
  
}

```

To create a controller, that can be reused:

* add `super.setMode(Mode.REUSE);` to the `start`-method of the controller

* use the `activate`-method to load data, set up the compoment, etc.

With `Mode` set to `REUSE`, Nalu will compare the last executed hash with the new one. In case both hashes are not equal, Nalu will work as always, creating everything, etc.

In case the last used hash is equal to the current hash, Nalu will execute the following steps:

* call the `mayStop`-method (so, the routing can be interrupted)

* call `deactivate`-method to deactivate the controller/composites

* inject the new parameter values into the controller

* call the `activate`-method

### Component Creation
Nalu will automatically create a component using the Java 'new'-command and inject the component into the controller. Therefore the component needs to have a zero argument constructor.

In some cases this might be a problem.

You can tell Nalu to use a method inside the controller and create the component by your own. To do so, you have to annotate the controller with `IsPopUpComponentCreator<V>` and implement a `createPopUpComponent`-method.

```java_holder_method_tree
@PopUpController(...)
public class MyController
  extends AbstractPopUpComponentController<MyContext, IMyComponent>
  implements IMyComponent.Controller,
             IsPopUpComponentCreator<IMyComponent> {

  ...


  @Override
  public IMyComponent createPopUpComponent() {
    return new MyComponent();
  }
}

```

Note: At the time Nalu calls the `createPopUpComponent`-method, the context is already injected.

## Multiple Route Support
A controller in Nalu can be atteched to more than one route. To define a controller for several routes, use a list of Strings for the `route`-attribute.


```java_holder_method_tree
@Controller(route = { "/shell/route01/route02/:id",
                      "/shell/route03/:id/route04",
                      "/shell/route01/route02/route03/:id" },
            selector = "content",
            componentInterface = MultiRouteComponent.class,
            component = MultiRouteComponentImpl.class)
public class MultiRouteController
  extends AbstractComponentController<MultiRouteContext, MultiRouteComponent, HTMLElement>
  implements MultiRouteComponent.Controller {

  private String id;

  public MultiRouteController() {
  }
  
  @AcceptParameter("id")
  public void setId(String s) {
    this.id = (s != null) ? s : "";
  }
}
```

Nalu will validate, that

* all routes have the same number of parameters
* all parameters have the same name
* there is no duplicate route

## Caching
Nalu provides a caching mechanism. This allows to store a controller/component for a route. Next the route will be used, Nalu restores the cached controller/component instead of creating a new controller and component.

To tell Nalu to cache a controller/component, use the `router.storeInCache(this)`-command inside the controller. Now, caching for this route is active. To stop caching, call `router.removeFromCache(this)`.

You can also cache composites.

Since 1.3.2 there is also a possibility to cache a composite as a singleton, so different sites can share a composite with the same state. You can configure this with the attribute scope in the annotation @Composites of the controllers which contain the composites:

```java_holder_method_tree
  @Composites({ @Composite(name = "myComposite",
                           compositeController = MyComposite.class,
                           selector = "my-composite",
                           scope = Scope.GLOBAL) })
  public class MyCompositeContainerController {
    
    ...
  }
```

There are two kind of scopes:

* Scope.GLOBAL to cache the composite as a singleton (share between components / sites)
* Scope.LOCAL to cache the composite only for this component / site (default)

## Reusing Controllers & Components

In case you have a controller/component pair (using a route f.e. like this: `/application/person/detail/:id`) and you want to page through the person data, which means, the only thing that changes is the `id`, you can do this by using the same route and only changing the id. Nalu will always create a new controller/component pair in case of routing.

Starting with version 2.1.0, Nalu will offer a new way to deal with this use case. Instead of always creating new components, you can tell Nalu to reuse them. To do so, just use `super.setMode(Mode.REUSE);`. this is the example code of a controller:

```java_holder_method_tree
@Controller(route = "/application/person/detail/:id",
            selector = "content",
            componentInterface = IDetailComponent.class,
            component = DetailComponent.class)
public class DetailController
    extends AbstractComponentController<Context, IDetailComponent, HTMLElement>
    implements IDetailComponent.Controller,
               IsComponentCreator<IDetailComponent> {
  
  private long id;
  
  public DetailController() {
  }
  
  @Override
  public void start() {
    super.setMode(Mode.REUSE);
  }
  
  @Override
  public void activate() {
    // ToDo: load data, set up component, etc.
  }
  
  @AcceptParameter("id")
  public void setId(String id)
      throws RoutingInterceptionException {
    try {
      this.id = Long.parseLong(id);
    } catch (NumberFormatException e) {
      // ToDo: error handling 
    }
  }
  
}

```

To create a controller, that can be reused:

* add `super.setMode(Mode.REUSE);` to the `start`-method of the controller

* use the `activate`-method to load data, set up the compoment, etc.

With `Mode` set to `REUSE`, Nalu will compare the last executed hash with the new one. In case both hashes are not equal, Nalu will work as always, creating everything, etc.

In case the last used hash is equal to the current hash, Nalu will execute the following steps:

* call the `mayStop`-method (so, the routing can be interrupted)

* call `deactivate`-method to deactivate the controller/composites

* inject the new parameter values into the controller

* call the `activate`-method

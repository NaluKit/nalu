# Application Context
Inside a Nalu application the context is the store of all shared application information.

The context will be instantiated by the framework and **can not be overriden or set by the application** with another instance!  
It is injected in every controller, filter, handler and the loader. All controllers, handlers, filters and the loader will have access to the same instance.

This is a good place to store information that should be application wide available.

The context is a required attribute of the `@Application`- and (in case of a multi module project) `@Module`-annotation.

There are two different ways of implementing the context depending of the type of the Nalu application:

* a single module project

* a multi module project

## Single Module Implementation
Inside a single module application you can use any Pojo you like. You only need to add the `IsContext`-interface. The simplest implementation of a context looks like that:

```java
public class MyApplicationContext
  implements IsContext {
  
  private String attribute;
  
  public MyApplicationContext() {
  }
  
  public String getAttribute() {
    return this.attribute;
  }
  
  public void setAttribute(String attribute) {
    this.attirbute = attribute;
  }
}
```
To inform Nalu about the context, you have to set the attribute **context** of the `Application`-annotation:

```Java
@Application(loader = MyApplicationLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class)
interface MyApplication
  extends IsApplication {
}
```

In case you plan to add modules in the future, you should consider the use of the `AbstractMainContext`-class.

See **Multi Module Implementation** for more information.

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
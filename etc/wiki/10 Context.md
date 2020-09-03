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
Inside a multi module application you need a different implementation of the context. (See the Module wiki page for more information). One motivation for the choosen implementation is to avoid a common project which contains the context class. This will improve the reuse of child modules in Nalu.

In a multi module application you need to define a 'MainContext' for the main application and for each sub module a 'ModuleContext'.

### MainContext
To define a 'MainContext' for the root application you need to extend the `AbstractMainContext`-class. The `AbstractMainContext`-class uses a data store (implemented as Map) to store the application data.

The `AbstractMainContext`-class looks like that:
```java
/**
 * <p>
 * Abstract context - base class for application context in
 * a multi module environment.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * environment
 */
public abstract class AbstractMainContext
    implements IsMainContext {

  /* application data store - available in main- and sub-modules */
  private Context context;

  public AbstractMainContext() {
    this.context = new Context();
  }

  /**
   * Gets the application context
   *
   * @return application context
   */
  public Context getContext() {
    return this.context;
  }

}
```
The `Context`-class is at least nothing else than a wrapper for a `Map`:
```java
public class Context {

  /* data store */
  private Map<String, Object> dataStore;

  public Context() {
    this.dataStore = new HashMap<>();
  }

  /**
   * Gets a value from the data store
   *
   * @param key key of the stored data
   * @return the stored value
   */
  public Object get(String key) {
    return this.dataStore.get(key);
  }

  /**
   * Sets a value in the data store
   *
   * @param key   key of the stored data
   * @param value value to store
   */
  public void put(String key,
                  Object value) {

    this.dataStore.put(key,
                       value);
  }

}
```
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

### Local (only inside the main module) Used Data
Data, which should not be shared with the sub modules, can be stored in normal instance variables. These variables will not be shared with the context of a sub module.

**Note: The MainContext will also work inside a single module application.**

## ModuleContext
Each sub module of an application needs it's own `ModuleContext`.

To define a 'ModuleContext' for each sub module of an application, you need to extend the `AbstractModuleContext`-class. The `AbstractModuleContext`-class uses a data store (implemented as Map) to store the application data and another data store (implemented as Map) to store the local data.

The `AbstractModuleContext`-class looks like that:
```java
/**
 * <p>
 * Abstract context base class to use inside moduls.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * environment
 */
public abstract class AbstractModuleContext
    implements IsModuleContext {

  /* context - available in main- and sub-modules */
  private Context context;
  /* context - available only in sub-module */
  private Context localContext;

  public AbstractModuleContext() {
    this.localContext = new Context();
  }

  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public Context getContext() {
    return this.context;
  }

  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public Context getLocalContext() {
    return this.localContext;
  }

  /**
   * Sets the application context
   *
   * @param context application context
   */
  @Override
  @NaluInternalUse
  public void setApplicationContext(Context context) {
    this.context = context;
  }

}
```
Once the context of the sub module is created, you can access the global storage using the `getContext()`- method and the local storage using the `getLocalContext()`-method.

**Data stored inside the local data store is only available inside the sub module!**

The `AbstractModuleContext`-class implementation will also use the `Context`-class.

Here is an example of sub module context:
```java
public class MySubModuleContext
  extends AbstractModuleContext {
  
  private final static String ATTRIBUTE_KEY       = "attribute";
  private final static String LOCAL_ATTRIBUTE_KEY = "local-attribute";
   
  /* only visible inside the sub module */
  private MyDataObject myDataObject;

  public MySubModuleContext() {
  }
  
  public String getAttribute() {
    return (String) this.getContext().get(MyApplicationContext.ATTRIBUTE_KEY);
  }
  
  public void setAttribute(String attribute) {
    this.getContext().put(MyApplicationContext.ATTRIBUTE_KEY, attribute);
  }
 
  public MyObject getLocalAttribute() {
    return (MyObject) this.getLocalContext().get(MyApplicationContext.LOCAL_ATTRIBUTE_KEY);
  }
  
  public void setAttribute(MyObject myObject) {
    this.getLocalContext().put(MyApplicationContext.LOCAL_ATTRIBUTE_KEY, myObject);
  }
   
  public MyDataObject getMyDataObject() {
    return this.myDataObject;
  }
  
  public void setMyDataObject(MyDataObject myDataObject) {
    this.myDataObject = myDataObject;
  }
}
```
Of course, you can also use instance variables instead of the local data store, to store local values inside your module.

Because the `AbstractModuleContext`-class implements the `IsModuleContext`-interface, you can use the `MyApplicationContext`-class inside the `Module`-annotation without any problem.

### Note
This implementation might look a little bit boiler-plated, but it helps you to avoid a common project where all modules and the main module depend on!
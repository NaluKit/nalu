# Application Context
Inside a Nalu application the contaxt is the store of all shared application information.

The context will be instantiated by the framework and injected in every controller, filter, handler and the loader. All controllers, handlers, filters and the loader will have access to the same instance.

This is a good place to store informations that should be application wide available.

The context is a required attribute of the `Application`- and (in case of a multi module project) `Module`-annotation.

There are two different ways:
* a single module project
* a multi module project

Depending on the type of the project you **need** diffferent implementations.

## Single module implemention
Inside a single module application you can use any Pojo you like. You only need to add the `IsContext`-interface. The simplest implementation of a context looks like that:

```Java
public class MyApplicationContext
  implements IsContext {
  
  private String attribute;
  
  public MyApplicationContext() {
  }
  
  public String getAttribute() {
    return this.attribute;
  }
  
  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }
}
```
To inform Nalu about the context, you have to set the attribute **context** of the `Application`-annotation:

```Java
@Application(loader = MyApplicationLoader.class,
             startRoute = "/application/search",
             context = MyApplicationContext.class,
             routeError = "/errorShell/error")
interface MyApplication
  extends IsApplication {
}
```

In case you plan to add modules in the future, you should consider the use of the `AbstractMainContext`-class. See **Multi module implementation** for more information.


## Multi module implementation
Inside a multi module application you need a different implementation. (See the Module wiki page for more inforamtion).

In a multi module application you need to define a 'MainContext' for the main application and for each sub module a 'ModuleContext'.

### MainContext
To define a 'MainContext' for the root application you need to extend the `AbstractMainContext`-class. The `AbstractMainContext`-class uses a data store (implemented as Map) to store the application data.

```Java
public class Context {

  /* datastore */
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
To access the data store, the `AbstractMainContext`-class provides a `getContext`-method. The above example of the implementation fo the single module context will look like that:

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
Because the `AbstractMainContext`-class implements the `IsMainContext`-interface which extends the`IsContext`-interface, you can use the `MyApplicationContext`-class inside the `Application`-annotation without any problem.

Data, which should not be shared with the sub modules, can be stored in normal instance variables. These variables will not be shared with the context of a sub module.

**Note: The MainContext will also work inside a singe module application.**

## ModuleContext
To define a 'ModuleContext' for each sub module of an application, you need to extend the `AbstractModuleContext`-class. The `AbstractModuleContext`-class uses a data store (implemented as Map) to store the application data and another data store (implemented as Map) to store the local data.

**Data stored inside the local data store is only available inside the sub module!**

The `AbstractModuleContext`-class implelemtation will also use the `Context`-class.

To access the global data store, the `AbstractModuleContext`-class provides a `getContext`-method. To access the local data store use the `getLocalContext()`-method.

Here is an example of module context:

```Java
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
This implementation might look a little bit boiler-platted, but it helps you to avoid a common project where all modules and the main module depend on!
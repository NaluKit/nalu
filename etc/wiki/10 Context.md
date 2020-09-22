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
Inside a multi module application you need a different implementation of the context. (See the [Module wiki page](https://github.com/NaluKit/nalu/wiki/15.-Multi-Module-Project) for more information).
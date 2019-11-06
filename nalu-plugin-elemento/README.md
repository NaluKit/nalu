# Nalu Elemento Plugin

For Elemento there's a dedicated plugin which supports org.jboss.gwt.elemento.core.IsElement<E> as widget type.


The plugin adds support for having components which use either:

* `Iterable<IsElement<E>>`
* `Iterable<HTMLElement>`
* `IsElement<E>`
* `HTMLElement`

To use this plugin in your application, add the following lines to your POM:

```xml
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-elemento</artifactId>
    <version>LATEST</version>
</dependency>
```

**The nalu-plugin-elemento can also be used with Domino-ui.**
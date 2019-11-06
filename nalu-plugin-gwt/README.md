# Nalu GWT Plugin

In case your working with widgets based on GWT's widget class, use the **nalu-gwt-plugin**.

The plugin adds support for having components which use either:

* `HTMLElement`

To use this plugin in your application, add the following lines to your POM:

```xml
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-gwt</artifactId>
    <version>LATEST</version>
</dependency>
```

The plugin requires, that you also add the **nalu-plugin-gwt-processor**:

```xml
<dependency>
  <groupId>com.github.nalukit</groupId>
  <artifactId>nalu-plugin-gwt-processor</artifactId>
  <version>LATEST</version>
  <scope>provided</scope>
</dependency>
```
**The nalu-plugin-gwt can also be used with GXT.**
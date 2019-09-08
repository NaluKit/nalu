# Nalu Plugin GWT
This module contains the classes needed in case you are using Nalu with a GWT based widget library.

Examples of GWT based widget libraries are:

* vanilla GWT
* GXT
* SmartGWT

In case your are using a GWT based widget library, add this module to the dependencies using:
```xml
    <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt</artifactId>
      <version>LATEST</version>
    </dependency>
```
Keep in mind, when using this plugin, you have to add also the processor module:
```xml
    <dependency>
      <groupId>com.github.nalukit</groupId>
      <artifactId>nalu-plugin-gwt-processor</artifactId>
      <version>LATEST</version>
    </dependency>
```


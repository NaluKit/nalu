# Nalu Domino-UI 2 Plugin

In case your working with widgets based on Domino-UI v2, the **nalu-domino-ui-2-plugin** will improve your coding experience.

Ths plugins adds several new classes for controllers and components. At least these classes extends the existing one and add improvements for using Domino-UI v2.

These improvements are:

1. because Nalu knows now, that we are using Domino-UI v2, there is no need to set teh widget type inside the generics. By default, it is set to **IsElement<?>**.

2. All abstract classes, that represents visual components, implements the **DominoCss**- and **ElementsFactory**-interfaces, so that the CSS class definitions and elements can be used directly and without adding the interface to each class.


The plugin adds support for having components which use either:

* Domino-UI (Version 2)

## Configuration

To use this plugin in your application, add the following lines to your POM:

```xml
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>nalu-plugin-domino-v2</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage

To get the benefits of the improvements, the new classes and interfaces have to be used. 

Here a list of the classes:

| Nalu classes usually used             | use with Domino V2                            |
|---------------------------------------|-----------------------------------------------|
| AbstractBlockComponent                | AbstractDominoV2BlockComponent                |
| AbstractBlockComponentController      | AbstractDominoV2BlockComponentController      |
| AbstractComponent                     | AbstractDominoV2Component                     |
| AbstractComponentController           | AbstractDominoV2ComponentController           |
| AbstractCompositeComponent            | AbstractDominoV2CompositeComponent            |
| AbstractCompositeController           | AbstractDominoV2CompositeController           |
| AbstractErrorPopUpComponent           | AbstractDominoV2ErrorPopUpComponent           |
| AbstractErrorPopUpComponentController | AbstractDominoV2ErrorPopUpComponentController |
| AbstractPopUpComponent                | AbstractDominoV2PopUpComponent                |
| AbstractPopUpComponentController      | AbstractDominoV2PopUpComponentController      |
| AbstractShell                         | AbstractDominoV2Shell                         |
| IsBlockComponent                      | IsDominoV2BlockComponent                      |
| IsBlockComponentController            | IsDominoV2BlockComponentController            |
| IsComponent                           | IsDominoV2Component                           |
| IsComposite                           | IsDominoV2Composite                           |
| IsCompositeComponent                  | IsDominoV2CompositeComponent                  |
| IsController                          | IsDominoV2Controller                          |
| IsErrorPopUpComponent                 | IsDominoV2ErrorPopUpComponent                 |
| IsPopUpComponent                      | IsDominoV2PopUpComponent                      |
| IsPopUpController                     | IsDominoV2PopUpController                     |
| IsShell                               | IsDominoV2Shell                               |
| IsShowBlockCondition                  | IsDominoV2ShowBlockCondition                  |
| IsShowPopUpCondition                  | IsDominoV2ShowPopUpCondition                  |


Of course, it is possible to use the **nalu-plugin-elemental2** plugin or use the default classes with this plugin.
This will work without any problems, but requires more coding.

## Notes

### Incubator

**This plugin is experimental and might change dur to changes in Domino-UI*.*

### Compatibility

This plugin can not be used wit Domino-UI version 1.x.x!


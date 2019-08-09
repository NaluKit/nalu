
# Shell

The shell is the root view of the application. It divides the screen in several areas, which can be used to add components. A Nalu application supports more then one shell. To define which shell should currently be used, add the name of the shell as first part of the route.

A route in Nalu will look like that:

```
/loginShell/login
```

This route tells Nalu to use the shell with the name `loginShell` and use a controller that uses `loginShell/login` as route. In case another route has a different first part, Nalu will remove the current shell and add the new shell to the viewport.

If there is a need to attach handlers to DOM elements, you have to override the ```bind```-method.

Depending on the plugin you are using, you have to tell Nalu the extension points in different ways.

## Defining a shell in Nalu
To define a shell in Nalu, you have to use the `@Shell`-annotation inside a shell class.

```Java
@Shell("applicationShell")
public class MyApplicationShell
    extends AbstractShell<MyApplicationContext> {
  
  ...
  
}
```

The code above will define a shell which can be referenced by the name 'applicationShell'.

To tell Nalu, which shell is to be used, the name of the shell must be the first part of the route:

`/applicationShell/detail`

Nalu will look for a shell defined with the name 'applicationShell' and add it to the viewport. In case that a shell is already added to the viewport, Nalu will remove the current shell, call the `onDetach`-method and add the new shell to the viewport.

Depending on the Nalu processor plugin that is used, a shell implementation may look like this:

## Nalu Elemental2 plugin
In case the **nalu-plugin-elemental2** is used, Nalu uses the id to look for nodes inside the dom.

An implementation using the nalu-plugin-elemental2 looks like this:

```Java
@Shell("applicationShell")
public class ApplicationShell
    extends AbstractShell<MyApplicationContext> {

  public Shell() {
  }

  @Override
  public void attachShell() {
    document.body.appendChild(this.render());
  }

  private HTMLElement render() {
    document.body.style.margin = CSSProperties.MarginUnionType.of(0);

    return div().css("shell")
                .add(createNorth())
                .add(createSouth())
                .add(div().css("navigation")
                          .attr(Nalu.NALU_ID_ATTRIBUTE,
                                "navigation")
                          .asElement())
                .add(div().css("shellContent")
                          .attr(Nalu.NALU_ID_ATTRIBUTE,
                                "content")
                          .asElement())
                .asElement();
  }

  private Element createNorth() {
    return header().css("header")
                   .attr(Nalu.NALU_ID_ATTRIBUTE,
                         "header")
                   .asElement();
  }

  private Element createSouth() {
    return footer().css("footer")
                   .attr(Nalu.NALU_ID_ATTRIBUTE,
                         "footer")
                   .asElement();
  }
}
```


## Nalu GWT plugin
In case the **nalu-plugin-gwt** is used, Nalu uses the ```add```-method to add a widget to an panel. The code for the add is generated via a processor. Therefore it is necessary to have package protected instance variables which are annotated with ```@Selector("[name of the selector]")```. The processor will generate the code to attach a widget.

**Important: you need to create a provider in your source.**

To do so, implement these two lines inside the ```bind```-method:

```Java
    IsSelectorProvider<Shell> provider = new ShellSelectorProviderImpl();
    provider.initialize(this);
```

Here is an example of a Shell implementation using the nalu-plugin-gwt:
```Java
@Shell("applicationShell")
public class ApplicationShell
  extends AbstractShell<MyApplicationContext> {

  private SimpleLayoutPanel headerWidget;
  private ResizeLayoutPanel footerWidget;
  private SimpleLayoutPanel navigationWidget;
  private SimpleLayoutPanel contentWidget;
  private ResizeLayoutPanel shell;
  private ApplicationCss    style;

  public Shell() {
    super();
  }

  @Override
  public void attachShell() {
    RootLayoutPanel.get()
                   .add(this.render());
  }

  private Widget render() {
    this.style = ApplicationStyleFactory.get()
                                        .getStyle();

    shell = new ResizeLayoutPanel();
    shell.setSize("100%",
                  "100%");
    //shell.addResizeHandler(event -> forceLayout());

    DockLayoutPanel panel = new DockLayoutPanel(Style.Unit.PX);
    panel.setSize("100%",
                  "100%");
    shell.add(panel);

    this.headerWidget = createNorth();
    panel.addNorth(this.headerWidget,
                   128);

    this.footerWidget = createSouth();
    panel.addSouth(this.footerWidget,
                   42);

    this.navigationWidget = createNavigation();
    panel.addWest(this.navigationWidget,
                  212);

    this.contentWidget = createContent();
    panel.add(this.contentWidget);

    return panel;
  }

  private SimpleLayoutPanel createNorth() {
    SimpleLayoutPanel panel = new SimpleLayoutPanel();
    panel.addStyleName(style.headerPanel());
    panel.getElement()
         .setId("header");
    return panel;
  }

  private ResizeLayoutPanel createSouth() {
    ResizeLayoutPanel footerPanel = new ResizeLayoutPanel();
    footerPanel.getElement()
               .setId("footer");
    return footerPanel;
  }

  private SimpleLayoutPanel createNavigation() {
    SimpleLayoutPanel panel = new SimpleLayoutPanel();
    panel.addStyleName(style.navigationPanel());
    panel.getElement()
         .setId("navigation");
    return panel;
  }

  private SimpleLayoutPanel createContent() {
    SimpleLayoutPanel panel = new SimpleLayoutPanel();
    panel.getElement()
         .setId("content");
    return panel;
  }

  @Override
  public void bind(ShellLoader loader)
    throws RoutingInterceptionException {
    IsSelectorProvider<Shell> provider = new ShellSelectorProviderImpl();
    provider.initialize(this);
    DomGlobal.window.alert("Stop inside bind-methode of Login-Shell");
    // call loader.continueLoading() to tell Nalu to continue
    loader.continueLoading();
  }

  @Selector("header")
  public void setHeader(Widget widget) {
    this.headerWidget.clear();
    this.headerWidget.add(widget);
  }

  @Selector("footer")
  public void setFooter(Widget widget) {
    this.footerWidget.clear();
    this.footerWidget.add(widget);
  }

  @Selector("navigation")
  public void setNavigation(Widget widget) {
    this.navigationWidget.clear();
    this.navigationWidget.add(widget);
  }

  @Selector("content")
  public void setContent(Widget widget) {
    this.contentWidget.clear();
    this.contentWidget.add(widget);
  }
}
```


## Async bind Method (since v1.3.0)
Nalu will call the `bind` method before the shell is added to the viewport. This is an internal method, that can be overwritten. That's a good place to do some preparing actions. The method is asynchron. This makes it possible to do a server call before Nalu will continue. To tell Nalu to continue, call `loader.continueLoading()`. In case you want to interrupt the loading, just do not call `loader.continueLoading()` and use the router to route to another place.

Example of a bind mehtod inside the shell.

```java
  @Override
  public void bind(ShellLoader loader)
    throws RoutingInterceptionException {

    // call loader.continueLoading() to tell Nalu to continue
    loader.continueLoading();

  }
```

## Post Attach
Nalu will call the `onAttachedComponent`-method after a new component is attached. If you need to do something after a new component is attached to the dom, this is good place to do so. F.e.: in case you are using GXT and want to do a `forceLayout` after a component is attached, use the following code:

```Java
  @Override
  public void onAttachedComponent() {
    this.shell.forceLayout();
  }
```


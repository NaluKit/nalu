



#TBD








# Tracking (since v1.2.0)

## Important Note!
Tracking user behavior is regulated by law in some countries!

Respect the privacy of your users. In case you want to track user behavior, you have to inform the user. In many cases you need a permission from the user! **Check the local laws!**

## Creating a Tracker
To create a tracker in Nalu, you have to create a class that `extends AbstractTracker<C>`. The class needs to implement two methods:

* **bind()**: This method will be called, once the tracker gets created.

* **track(String route, String... parms)**: this method will be called by the framework to track the routes, the user selects.

A simple tracker that only tracks the routes looks like this:

```Java
public class ApplicationTracker
    extends AbstractTracker<NaluLoginApplicationContext> {

  private String         url;
  private RequestBuilder builder;

  @Override
  public void bind() {
    url = GWT.getModuleBaseURL();
    url = url + "tracking";
    url = URL.encode(url);

    this.builder = new RequestBuilder(RequestBuilder.POST,
                                      url);
  }

  @Override
  public void track(String route,
                    String... params) {
    StringBuilder sb = createLogMessage(route,
                                        params);
    this.logActionInBrowser("Tracker: " + sb.toString());
    sendTrackingMessageToServer(sb.toString());
  }

  private void sendTrackingMessageToServer(String trackingMessage) {
    try {
      builder.sendRequest("Tracker: " + trackingMessage,
                          new RequestCallback() {
                            public void onError(Request request,
                                                Throwable exception) {
                              // no doubt ... we will ignore every error!
                            }

                            public void onResponseReceived(Request request,
                                                           Response response) {
                              // oh ... success ... but who concerns ... :-)
                            }
                          });
    } catch (RequestException e) {
      // no doubt ... we will ignore every error!
    }
  }

  private void logActionInBrowser(String message) {
    GWT.log(message);
  }

}
```

Next, you need to inform Nalu that there is a tracker. To do so, add the `@Tracker` annotatin to the application interface:

```Java
@Application(loader = NaluLoginApplicationLoader.class,
             startRoute = "/loginShell/login",
             context = NaluLoginApplicationContext.class,
             routeError = "/errorShell/error")
@Tracker(ApplicationTracker.class)
interface NaluLoginApplication
    extends IsApplication {

}
```

Now, Nalu will call the `track`-method everytime a routing occurs. All you have to do is to code the server call and define the data to send to the server.

Inside the tracker you will have access to the application context and to the event bus.

## Tracking none Routing Actions
In many cases the routing informations are not enough to track. To track these things:

* create an event

* add a handler inside the `bind`-method

* handle the event

Inside the NaluDominoLoginPluginNoHashApplication example, a tracking is implemented. The implementation can be found here: [NaluDominoLoginPluginNoHashApplication](https://github.com/NaluKit/nalu-examples/tree/master/NaluDominoLoginPluginNoHashApplication)

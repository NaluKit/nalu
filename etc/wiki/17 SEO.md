# SEO-Support

Nalu supports simply setting SEO meta tags and the page title.

Nalu will collect all SEO informations during a routing and will update the SEO informations at the end of a routing. To set a SEO-information call a `setter`-method.



   In case a routing is handled by more than one controller




## Title
A controller can set the host page title using:
```java
SeoDataProvider.get()
               .setTitle("New Page Title");
```
In case of a routing several controllers may handle the route.
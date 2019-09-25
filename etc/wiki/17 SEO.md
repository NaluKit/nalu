# SEO-Support

Nalu supports simply setting SEO meta tags and the page title.

Nalu will collect all SEO informations during a routing and will update the SEO informations at the end of a routing. To set a SEO-information call a `setter`-method.

`setter``methods are available for the following meta informations:

* **title**: iupdates the title of the host page

Standard meta tags:

* **description**: meta tag with name 'description'
* **keywords**: meta tag with name 'keywords'
* **robots**: meta tag with name 'robots'

Meta tags for Facebook integration:

* **og:title**: the 'og:title' meta tag
* **og:image**: the 'og:image' meta tag
* **og:type**: the 'og:type' meta tag
* **og:url**: the 'og:url' meta tag
* **og:sitename**: the 'og:sitename' meta tag
* **og:descritpion**: the 'og:description' meta tag

Meta tags for Twitter integration:

* **twitter:card**: the 'twitter:card' meta tag
* **twitter:title**: the 'twitter:title' meta tag
* **twitter:descritpion**: the 'twitter:description' meta tag
* **twitter:image**: the 'twitter:image' meta tag
* **twitter:site**: the 'twitter:site' meta tag
* **twitter:Creator**: the 'twitter:creator' meta tag

To set these values use the following code:

```java
     SeoDataProvider.get()
                     .setTitle("Example - Edit Persons Name: >>" + this.person.getName() + ", " + this.person.getFirstName());

      SeoDataProvider.get()
                     .setDescription("Here you will edit the person selected from the result list");
      SeoDataProvider.get()
                     .setKeywords("Person, edit, cool");
      SeoDataProvider.get()
                     .setRobots("index,follow");
      SeoDataProvider.get()
                     .setOgDescription("I am OG description ...");
      SeoDataProvider.get()
                     .setOgImage("http://www.gwtproject.org");
      SeoDataProvider.get()
                     .setOgSiteName("Max Mustermann");
      SeoDataProvider.get()
                     .setOgTitle("Title: Max Mustermann");
      SeoDataProvider.get()
                     .setOgType("Type: Max Mustermann");
      SeoDataProvider.get()
                     .setOgUrl("http://www.gwtproject.org");
                     
      SeoDataProvider.get()
                     .setTwitterCard("Twitter Card: Max Mustermann");
      SeoDataProvider.get()
                     .setTwitterCreator("Twitter Creator");
      SeoDataProvider.get()
                     .setTwitterDescription("Twitter DEscription");
      SeoDataProvider.get()
                     .setTwitterImage("http://www.gwtproject.org");
      SeoDataProvider.get()
                     .setTwitterSite("http://www.gwtproject.org");
      SeoDataProvider.get()
                     .setTwitterTitle("Twitter Tittle");
```

In case a routing is handled by more than one controller and two controllers will update the SEO data, Nalu will use the data the first controller has set!

In case a controller wants to make sure that the value is used, call: ` SeoDataProvider.get().setTwitterTitle("Twitter Tittle", true);`-method. In this case the value will be set even there is already a value.

After Nalu has updated the SEO tags, the data gets reseted. 

Nalu will only update the tags which gets change by the current routing. No tags will be removed!

**Note:**
Meta tags will only be updated in case the routing was successful!
package io.github.nalukit.nalu.client.seo;

import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;

import java.util.Objects;

public class SeoDataProvider {

  /* instance of the factory */
  public final static SeoDataProvider INSTANCE = new SeoDataProvider();

  /* Nalu plugin */
  private IsNaluProcessorPlugin plugin;
  /* data for next update */
  private SeoData               seoData;

  private SeoDataProvider() {
    this.seoData = new SeoData();
  }

  public void register(IsNaluProcessorPlugin plugin) {
    this.plugin = plugin;
  }

  public void setDescription(String description) {
    this.setDescription(description,
                        false);
  }

  public void setDescription(String description,
                             boolean important) {
    if (Objects.isNull(this.seoData.getDescription())) {
      this.seoData.setDescription(description);
    } else {
      if (important) {
        this.seoData.setDescription(description);
      }
    }
  }

  public void setRobots(String robots) {
    this.setRobots(robots,
                   false);
  }

  public void setRobots(String robots,
                        boolean important) {
    if (Objects.isNull(this.seoData.getRobots())) {
      this.seoData.setRobots(robots);
    } else {
      if (important) {
        this.seoData.setRobots(robots);
      }
    }
  }

  public void setKeywords(String keywords) {
    this.setKeywords(keywords,
                     false);
  }

  public void setKeywords(String keywords,
                          boolean important) {
    if (Objects.isNull(this.seoData.getKeywords())) {
      this.seoData.setKeywords(keywords);
    } else {
      if (important) {
        this.seoData.setKeywords(keywords);
      }
    }
  }

  public void setTitle(String title) {
    this.setTitle(title,
                  false);
  }

  public void setTitle(String title,
                       boolean important) {
    if (Objects.isNull(this.seoData.getTitle())) {
      this.seoData.setTitle(title);
    } else {
      if (important) {
        this.seoData.setTitle(title);
      }
    }
  }

  public void setOgTitle(String ogTitle) {
    this.setOgTitle(ogTitle,
                    false);
  }

  public void setOgTitle(String ogTitle,
                         boolean important) {
    if (Objects.isNull(this.seoData.getOgTitle())) {
      this.seoData.setOgTitle(ogTitle);
    } else {
      if (important) {
        this.seoData.setOgTitle(ogTitle);
      }
    }
  }

  public void setOgImage(String ogImage) {
    this.setOgImage(ogImage,
                    false);
  }

  public void setOgImage(String ogImage,
                         boolean important) {
    if (Objects.isNull(this.seoData.getOgImage())) {
      this.seoData.setOgImage(ogImage);
    } else {
      if (important) {
        this.seoData.setOgImage(ogImage);
      }
    }
  }

  public void setOgType(String ogType) {
    this.setOgType(ogType,
                   false);
  }

  public void setOgType(String ogType,
                        boolean important) {
    if (Objects.isNull(this.seoData.getOgType())) {
      this.seoData.setOgType(ogType);
    } else {
      if (important) {
        this.seoData.setOgType(ogType);
      }
    }
  }

  public void setOgUrl(String ogUrl) {
    this.setOgUrl(ogUrl,
                  false);
  }

  public void setOgUrl(String ogUrl,
                       boolean important) {
    if (Objects.isNull(this.seoData.getOgUrl())) {
      this.seoData.setOgUrl(ogUrl);
    } else {
      if (important) {
        this.seoData.setOgUrl(ogUrl);
      }
    }
  }

  public void setOgSiteName(String ogSiteName) {
    this.setOgSiteName(ogSiteName,
                       false);
  }

  public void setOgSiteName(String ogSiteName,
                            boolean important) {
    if (Objects.isNull(this.seoData.getOgSiteName())) {
      this.seoData.setOgSiteName(ogSiteName);
    } else {
      if (important) {
        this.seoData.setOgSiteName(ogSiteName);
      }
    }
  }

  public void setOgDescription(String ogDescription) {
    this.setOgDescription(ogDescription,
                          false);
  }

  public void setOgDescription(String ogDescription,
                               boolean important) {
    if (Objects.isNull(this.seoData.getOgDescription())) {
      this.seoData.setOgDescription(ogDescription);
    } else {
      if (important) {
        this.seoData.setOgDescription(ogDescription);
      }
    }
  }

  public void setTwitterCard(String twitterCard) {
    this.setTwitterCard(twitterCard,
                        false);
  }

  public void setTwitterCard(String twitterCard,
                             boolean important) {
    if (Objects.isNull(this.seoData.getTwitterCard())) {
      this.seoData.setTwitterCard(twitterCard);
    } else {
      if (important) {
        this.seoData.setTwitterCard(twitterCard);
      }
    }
  }

  public void setTwitterTitle(String twitterTitle) {
    this.setTwitterTitle(twitterTitle,
                         false);
  }

  public void setTwitterTitle(String twitterTitle,
                              boolean important) {
    if (Objects.isNull(this.seoData.getTwitterTitle())) {
      this.seoData.setTwitterTitle(twitterTitle);
    } else {
      if (important) {
        this.seoData.setTwitterTitle(twitterTitle);
      }
    }
  }

  public void setTwitterDescription(String twitterDescription) {
    this.setTwitterDescription(twitterDescription,
                               false);
  }

  public void setTwitterDescription(String twitterDescription,
                                    boolean important) {
    if (Objects.isNull(this.seoData.getTwitterDescription())) {
      this.seoData.setTwitterDescription(twitterDescription);
    } else {
      if (important) {
        this.seoData.setTwitterDescription(twitterDescription);
      }
    }
  }

  public void setTwitterImage(String twitterImage) {
    this.setTwitterImage(twitterImage,
                         false);
  }

  public void setTwitterImage(String twitterImage,
                              boolean important) {
    if (Objects.isNull(this.seoData.getTwitterImage())) {
      this.seoData.setTwitterImage(twitterImage);
    } else {
      if (important) {
        this.seoData.setTwitterImage(twitterImage);
      }
    }
  }

  public void setTwitterSite(String twitterSite) {
    this.setTwitterSite(twitterSite,
                        false);
  }

  public void setTwitterSite(String twitterSite,
                             boolean important) {
    if (Objects.isNull(this.seoData.getTwitterSite())) {
      this.seoData.setTwitterSite(twitterSite);
    } else {
      if (important) {
        this.seoData.setTwitterSite(twitterSite);
      }
    }
  }

  public void setTwitterCreator(String twitterCreator) {
    this.setTwitterCreator(twitterCreator,
                           false);
  }

  public void setTwitterCreator(String twitterCreator,
                                boolean important) {
    if (Objects.isNull(this.seoData.getTwitterCreator())) {
      this.seoData.setTwitterCreator(twitterCreator);
    } else {
      if (important) {
        this.seoData.setTwitterCreator(twitterCreator);
      }
    }
  }

  /**
   * Updates the meta data of the page.
   */
  public void update() {
    // update ...
    if (!Objects.isNull(this.seoData.getTitle())) {
      this.plugin.updateTitle(this.seoData.getTitle());
    }
    if (!Objects.isNull(this.seoData.getDescription())) {
      this.plugin.updateMetaNameContent("description",
                                        this.seoData.getDescription());
    }
    if (!Objects.isNull(this.seoData.getKeywords())) {
      this.plugin.updateMetaNameContent("keywords",
                                        this.seoData.getKeywords());
    }

    if (!Objects.isNull(this.seoData.getOgTitle())) {
      this.plugin.updateMetaPropertyContent("og:title",
                                            this.seoData.getOgTitle());
    }
    if (!Objects.isNull(this.seoData.getOgImage())) {
      this.plugin.updateMetaPropertyContent("og:image",
                                            this.seoData.getOgImage());
    }
    if (!Objects.isNull(this.seoData.getOgType())) {
      this.plugin.updateMetaPropertyContent("og:type",
                                            this.seoData.getOgType());
    }
    if (!Objects.isNull(this.seoData.getOgUrl())) {
      this.plugin.updateMetaPropertyContent("og:url",
                                            this.seoData.getOgUrl());
    }
    if (!Objects.isNull(this.seoData.getOgSiteName())) {
      this.plugin.updateMetaPropertyContent("og:site_name",
                                            this.seoData.getOgSiteName());
    }
    if (!Objects.isNull(this.seoData.getOgDescription())) {
      this.plugin.updateMetaPropertyContent("og:description",
                                            this.seoData.getOgDescription());
    }

    if (!Objects.isNull(this.seoData.getTwitterCard())) {
      this.plugin.updateMetaNameContent("twitter:card",
                                        this.seoData.getTwitterCard());
    }
    if (!Objects.isNull(this.seoData.getTwitterCreator())) {
      this.plugin.updateMetaNameContent("twitter:creator",
                                        this.seoData.getTwitterCreator());
    }
    if (!Objects.isNull(this.seoData.getTwitterDescription())) {
      this.plugin.updateMetaNameContent("twitter:description",
                                        this.seoData.getTwitterDescription());
    }
    if (!Objects.isNull(this.seoData.getTwitterImage())) {
      this.plugin.updateMetaNameContent("twitter:image",
                                        this.seoData.getTwitterImage());
    }
    if (!Objects.isNull(this.seoData.getTwitterSite())) {
      this.plugin.updateMetaNameContent("twitter:site",
                                        this.seoData.getTwitterSite());
    }
    if (!Objects.isNull(this.seoData.getTwitterTitle())) {
      this.plugin.updateMetaNameContent("twitter:title",
                                        this.seoData.getTwitterTitle());
    }

    // clear model
    this.seoData = new SeoData();
  }

}

package com.github.nalukit.nalu.client.seo;

import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;

import java.util.Objects;

public class SeoFactory {

  /* instance of the factory */
  private static SeoFactory            instance;
  /* Nalu plugin */
  private        IsNaluProcessorPlugin plugin;
  /* last used */
  private        SeoData               lastUsedSeoData;
  /* data dor next update */
  private        SeoData               seoData;

  private SeoFactory() {
  }

  public static SeoFactory get() {
    if (Objects.isNull(instance)) {
      instance = new SeoFactory();
    }
    return instance;
  }

  public void register(IsNaluProcessorPlugin plugin) {
    this.plugin = plugin;
  }

  public void updateDescription(String description) {
    this.updateDescription(description,
                           false);
  }

  public void updateDescription(String description,
                                boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getDescription())) {
      this.seoData.setDescription(description);
    } else {
      if (replaceExisting) {
        this.seoData.setDescription(description);
      }
    }
  }

  public void updateRobots(String robots) {
    this.updateRobots(robots,
                      false);
  }

  public void updateRobots(String robots,
                           boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getRobots())) {
      this.seoData.setRobots(robots);
    } else {
      if (replaceExisting) {
        this.seoData.setRobots(robots);
      }
    }
  }

  public void updateKeywords(String keywords) {
    this.updateKeywords(keywords,
                        false);
  }

  public void updateKeywords(String keywords,
                             boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getKeywords())) {
      this.seoData.setKeywords(keywords);
    } else {
      if (replaceExisting) {
        this.seoData.setKeywords(keywords);
      }
    }
  }

  public void updateTitle(String title) {
    this.updateTitle(title,
                     false);
  }

  public void updateTitle(String title,
                          boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTitle())) {
      this.seoData.setTitle(title);
    } else {
      if (replaceExisting) {
        this.seoData.setTitle(title);
      }
    }
  }

  public void updateOgTitle(String ogTitle) {
    this.updateOgTitle(ogTitle,
                       false);
  }

  public void updateOgTitle(String ogTitle,
                            boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgTitle())) {
      this.seoData.setOgTitle(ogTitle);
    } else {
      if (replaceExisting) {
        this.seoData.setOgTitle(ogTitle);
      }
    }
  }

  public void updateOgImage(String ogImage) {
    this.updateOgImage(ogImage,
                       false);
  }

  public void updateOgImage(String ogImage,
                            boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgImage())) {
      this.seoData.setOgImage(ogImage);
    } else {
      if (replaceExisting) {
        this.seoData.setOgImage(ogImage);
      }
    }
  }

  public void updateOgType(String ogType) {
    this.updateOgType(ogType,
                      false);
  }

  public void updateOgType(String ogType,
                           boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgType())) {
      this.seoData.setOgType(ogType);
    } else {
      if (replaceExisting) {
        this.seoData.setOgType(ogType);
      }
    }
  }

  public void updateOgUrl(String ogUrl) {
    this.updateOgUrl(ogUrl,
                     false);
  }

  public void updateOgUrl(String ogUrl,
                          boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgUrl())) {
      this.seoData.setOgUrl(ogUrl);
    } else {
      if (replaceExisting) {
        this.seoData.setOgUrl(ogUrl);
      }
    }
  }

  public void updateOgSiteName(String ogSiteName) {
    this.updateOgSiteName(ogSiteName,
                          false);
  }

  public void updateOgSiteName(String ogSiteName,
                               boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgSiteName())) {
      this.seoData.setOgSiteName(ogSiteName);
    } else {
      if (replaceExisting) {
        this.seoData.setOgSiteName(ogSiteName);
      }
    }
  }

  public void updateOgDescription(String ogDescription) {
    this.updateOgDescription(ogDescription,
                             false);
  }

  public void updateOgDescription(String ogDescription,
                                  boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getOgDescription())) {
      this.seoData.setOgDescription(ogDescription);
    } else {
      if (replaceExisting) {
        this.seoData.setOgDescription(ogDescription);
      }
    }
  }

  public void updateTwitterCard(String twitterCard) {
    this.updateTwitterCard(twitterCard,
                           false);
  }

  public void updateTwitterCard(String twitterCard,
                                boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterCard())) {
      this.seoData.setTwitterCard(twitterCard);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterCard(twitterCard);
      }
    }
  }

  public void updateTwitterTitle(String twitterTitle) {
    this.updateTwitterTitle(twitterTitle,
                            false);
  }

  public void updateTwitterTitle(String twitterTitle,
                                 boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterTitle())) {
      this.seoData.setTwitterTitle(twitterTitle);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterTitle(twitterTitle);
      }
    }
  }

  public void updateTwitterDescription(String twitterDescription) {
    this.updateTwitterDescription(twitterDescription,
                                  false);
  }

  public void updateTwitterDescription(String twitterDescription,
                                       boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterDescription())) {
      this.seoData.setTwitterDescription(twitterDescription);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterDescription(twitterDescription);
      }
    }
  }

  public void updateTwitterImage(String twitterImage) {
    this.updateTwitterImage(twitterImage,
                            false);
  }

  public void updateTwitterImage(String twitterImage,
                                 boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterImage())) {
      this.seoData.setTwitterImage(twitterImage);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterImage(twitterImage);
      }
    }
  }

  public void updateTwitterSite(String twitterSite) {
    this.updateTwitterSite(twitterSite,
                           false);
  }

  public void updateTwitterSite(String twitterSite,
                                boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterSite())) {
      this.seoData.setTwitterSite(twitterSite);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterSite(twitterSite);
      }
    }
  }

  public void updateTwitterCreator(String twitterCreator) {
    this.updateTwitterCreator(twitterCreator,
                              false);
  }

  public void updateTwitterCreator(String twitterCreator,
                                   boolean replaceExisting) {
    if (Objects.isNull(this.seoData.getTwitterCreator())) {
      this.seoData.setTwitterCreator(twitterCreator);
    } else {
      if (replaceExisting) {
        this.seoData.setTwitterCreator(twitterCreator);
      }
    }
  }

  /**
   * Updates the meta data of the page.
   */
  public void update() {
    // reset first ...
    this.remove();
    // create copy
    this.lastUsedSeoData = new SeoData(this.seoData);
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

  private void remove() {

    // reset last used data
    this.lastUsedSeoData = new SeoData();
  }

}

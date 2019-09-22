package com.github.nalukit.nalu.client.seo;

import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;

import java.util.Map;
import java.util.Objects;

public class SeoFactory {

  /* instance of the factory */
  private static SeoFactory            instance;
  /* Nalu plugin */
  private        IsNaluProcessorPlugin plugin;
  /* last used */
  private        String                lastUsedTitle;
  private        Map<String, String>   lastUsedMetaData;
  /* data dor next update */
  private        String                title;
  private        Map<String, String>   metaData;

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

  public void updateTitle(String title) {
    this.updateTitle(title,
                     false);
  }

  public void updateMetaData(String key,
                             String value) {
    this.updateMetaData(key,
                        value,
                        false);
  }

  public void updateMetaData(String key,
                             String value,
                             boolean replaceExisting) {
    if (Objects.isNull(this.metaData.get(key))) {
      this.metaData.put(key,
                        value);
    } else {
      if (replaceExisting) {
        this.metaData.put(key,
                          value);
      }
    }
  }

  public void updateTitle(String title,
                          boolean replaceExisting) {
    if (Objects.isNull(this.title)) {
      this.title = title;
    } else {
      if (replaceExisting) {
        this.title = title;
      }
    }
  }

  /**
   * Updates the meta data of the page.
   */
  public void update() {
    // reset first ...
    this.remove();
    // update title ...
    if (!Objects.isNull(this.title)) {
      this.plugin.updateTitle(this.title);
      this.lastUsedTitle = this.title;
      this.title = null;
    }

    // store the model
//    this.seoModel = seoModel;
  }

  private void remove() {

  }

}

package io.github.nalukit.nalu.client.seo;

public class SeoData {

  private String title;
  private String description;
  private String keywords;
  private String robots;

  private String ogTitle;
  private String ogImage;
  private String ogType;
  private String ogUrl;
  private String ogSiteName;
  private String ogDescription;

  private String twitterCard;
  private String twitterTitle;
  private String twitterDescription;
  private String twitterImage;
  private String twitterSite;
  private String twitterCreator;

  public SeoData() {
  }

  public SeoData(SeoData other) {
    this.title              = other.title;
    this.description        = other.description;
    this.keywords           = other.keywords;
    this.robots             = other.robots;
    this.ogTitle            = other.ogTitle;
    this.ogImage            = other.ogImage;
    this.ogType             = other.ogType;
    this.ogUrl              = other.ogUrl;
    this.ogSiteName         = other.ogSiteName;
    this.ogDescription      = other.ogDescription;
    this.twitterCard        = other.twitterCard;
    this.twitterTitle       = other.twitterTitle;
    this.twitterDescription = other.twitterDescription;
    this.twitterImage       = other.twitterImage;
    this.twitterSite        = other.twitterSite;
    this.twitterCreator     = other.twitterCreator;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getRobots() {
    return robots;
  }

  public void setRobots(String robots) {
    this.robots = robots;
  }

  public String getOgTitle() {
    return ogTitle;
  }

  public void setOgTitle(String ogTitle) {
    this.ogTitle = ogTitle;
  }

  public String getOgImage() {
    return ogImage;
  }

  public void setOgImage(String ogImage) {
    this.ogImage = ogImage;
  }

  public String getOgType() {
    return ogType;
  }

  public void setOgType(String ogType) {
    this.ogType = ogType;
  }

  public String getOgUrl() {
    return ogUrl;
  }

  public void setOgUrl(String ogUrl) {
    this.ogUrl = ogUrl;
  }

  public String getOgSiteName() {
    return ogSiteName;
  }

  public void setOgSiteName(String ogSiteName) {
    this.ogSiteName = ogSiteName;
  }

  public String getOgDescription() {
    return ogDescription;
  }

  public void setOgDescription(String ogDescription) {
    this.ogDescription = ogDescription;
  }

  public String getTwitterCard() {
    return twitterCard;
  }

  public void setTwitterCard(String twitterCard) {
    this.twitterCard = twitterCard;
  }

  public String getTwitterTitle() {
    return twitterTitle;
  }

  public void setTwitterTitle(String twitterTitle) {
    this.twitterTitle = twitterTitle;
  }

  public String getTwitterDescription() {
    return twitterDescription;
  }

  public void setTwitterDescription(String twitterDescription) {
    this.twitterDescription = twitterDescription;
  }

  public String getTwitterImage() {
    return twitterImage;
  }

  public void setTwitterImage(String twitterImage) {
    this.twitterImage = twitterImage;
  }

  public String getTwitterSite() {
    return twitterSite;
  }

  public void setTwitterSite(String twitterSite) {
    this.twitterSite = twitterSite;
  }

  public String getTwitterCreator() {
    return twitterCreator;
  }

  public void setTwitterCreator(String twitterCreator) {
    this.twitterCreator = twitterCreator;
  }

}

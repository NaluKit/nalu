package io.github.nalukit.nalu.client.context;

import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.Date;

/**
 * <p>
 * Abstract context base class to use inside modules.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * environment.
 */
public abstract class AbstractModuleContext
    implements IsModuleContext {

  private final static String APPLICATION_BUILD_TIME = "APPLICATION_BUILD_TIME";
  private final static String APPLICATION_VERSION    = "APPLICATION_VERSION";

  /* context - available in main- and sub-modules */
  private       ContextDataStore applicationContext;
  /* context - not manged by Nalu */
  private final ContextDataStore context;

  public AbstractModuleContext() {
    this.applicationContext = new ContextDataStore();
    this.context            = new ContextDataStore();
  }

  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public ContextDataStore getApplicationContext() {
    return this.applicationContext;
  }

  /**
   * Sets the application context.
   *
   * <b>DO NOT USE IT. THIS METHOD IS USED BY
   * THE FRAMEWORK AND USING IT MIGHT LEAD TO
   * UNEXPECTED RESULTS!</b>
   *
   * @param applicationContext context of the parent module
   */
  @Override
  public void setApplicationContext(ContextDataStore applicationContext) {
    this.applicationContext = applicationContext;
  }

  /**
   * Gets the local, not by Nalu managed, context
   *
   * @return local context
   */
  public ContextDataStore getContext() {
    return context;
  }

  /**
   * Returns the application build time set by the Nalu processor.
   *
   * @return build time of the application set by the Nalu processor
   */
  public Date getApplicationBuildTime() {
    return (Date) this.applicationContext.get(AbstractModuleContext.APPLICATION_BUILD_TIME);
  }

  /**
   * Sets the application build time.
   * <p>
   * Note:
   * Keep in mind, that Nalu will use the method.
   * Using the method inside your application will lead to
   * unexpected results.
   *
   * @param applicationBuildTime the application build time
   */
  @NaluInternalUse
  public final void setApplicationBuildTime(Date applicationBuildTime) {
    this.applicationContext.put(AbstractModuleContext.APPLICATION_BUILD_TIME,
                                applicationBuildTime);
  }

  /**
   * Returns the application version. The value can be set by using
   * the @Version annotation.
   *
   * @return version of the application set by the Version annotation
   */
  public String getApplicationVersion() {
    return (String) this.applicationContext.get(AbstractModuleContext.APPLICATION_VERSION);
  }

  /**
   * Sets the application version.
   * <p>
   * Note:
   * Keep in mind, that Nalu will use the method.
   * Using the method inside your application will lead to
   * unexpected results.
   *
   * @param applicationVersion the application version
   */
  @NaluInternalUse
  public final void setApplicationVersion(String applicationVersion) {
    this.applicationContext.put(AbstractModuleContext.APPLICATION_VERSION,
                                applicationVersion);
  }

}

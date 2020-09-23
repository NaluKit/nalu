package com.github.nalukit.nalu.client.context;

/**
 * <p>
 * Abstract context base class to use inside modules.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * environment.
 */
public abstract class AbstractModuleContext
    implements IsModuleContext {

  /* context - available in main- and sub-modules */
  private ContextDataStore applicationContext;
  /* context - not manged by Nalu */
  private ContextDataStore context;

  public AbstractModuleContext() {
    this.applicationContext = new ContextDataStore();
    this.context = new ContextDataStore();
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

}

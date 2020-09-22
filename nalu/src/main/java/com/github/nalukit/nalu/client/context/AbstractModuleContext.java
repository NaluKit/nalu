package com.github.nalukit.nalu.client.context;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

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
  private Context applicationContext;
  /* context - not manged by Nalu */
  private Context context;
  
  public AbstractModuleContext() {
    this.applicationContext = new Context();
    this.context            = new Context();
  }
  
  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public Context getApplicationContext() {
    return this.applicationContext;
  }
  
  /**
   * Sets the application context
   *
   * @param context application context
   */
  @NaluInternalUse
  public final void setApplicationContext(Context context) {
    this.applicationContext = context;
  }
  
  /**
   * Gets the local, not by Nalu managed, context
   *
   * @return local context
   */
  public Context getContext() {
    return context;
  }
  
}

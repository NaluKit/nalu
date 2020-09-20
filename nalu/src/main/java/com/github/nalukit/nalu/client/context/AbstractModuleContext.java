package com.github.nalukit.nalu.client.context;

import com.github.nalukit.nalu.client.context.module.IsModuleContext;
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
  private Context context;
  /* context - available only in sub-module */
  private Context localContext;
  
  public AbstractModuleContext() {
    this.localContext = new Context();
  }
  
  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public Context getContext() {
    return this.context;
  }
  
  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public Context getLocalContext() {
    return this.localContext;
  }
  
  /**
   * Sets the application context
   *
   * @param context application context
   */
  @Override
  @NaluInternalUse
  public final void setApplicationContext(Context context) {
    this.context = context;
  }
  
}

package com.github.nalukit.nalu.client.context;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

/**
 * <p>
 * Abstract context base class to use inside moduls.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * envirement
 */
public abstract class AbstractModuleContext
    implements IsModuleContext {

  /* application context - available in main- and sub-modules */
  private Context context;
  /* application context - available only in sub-module */
  private Context localContext;

  public AbstractModuleContext() {
    this.localContext = new Context();
  }

  /**
   * Gets the application context
   *
   * @return applicaitopn context
   */
  public Context getContext() {
    return this.context;
  }

  /**
   * Gets the application context
   *
   * @return application context
   */
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
  public void setApplicationContext(Context context) {
    this.context = context;
  }

}

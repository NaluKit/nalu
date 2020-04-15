package com.github.nalukit.nalu.client.context;

import com.github.nalukit.nalu.client.context.module.IsMainContext;

/**
 * <p>
 * Abstract context - base class for application context in
 * a multi module envirement.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * envirement
 */
public abstract class AbstractMainContext
    implements IsMainContext {
  
  /* application data store - available in main- and sub-modules */
  private Context context;
  
  public AbstractMainContext() {
    this.context = new Context();
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
  
}

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
  
  /* context - available in main- and sub-modules */
  private GlobalContext globalContext;
  
  public AbstractModuleContext() {
    this.globalContext = new GlobalContext();
  }
  
  /**
   * Gets the application context
   *
   * @return application context
   */
  @Override
  public GlobalContext getContext() {
    return this.globalContext;
  }
  
  /**
   * Sets the application context
   *
   * @param globalContext application context
   */
  @NaluInternalUse
  public final void setApplicationContext(GlobalContext globalContext) {
    this.globalContext = globalContext;
  }
  
}

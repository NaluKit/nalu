package com.github.nalukit.nalu.client.context;

/**
 * <p>
 * Abstract context base class to use inside the base application.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * envirement
 */
public abstract class AbstractContext
    implements IsContext {

  /* application context - available in main- and sub-modules */
  private Context context;

  public AbstractContext() {
    this.context = new Context();
  }

  /**
   * Gets the application context
   *
   * @return applicaitopn context
   */
  @Override
  public Context getContext() {
    return this.context;
  }

}

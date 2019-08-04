package com.github.nalukit.nalu.client.context;

/**
 * <p>
 * Abstract context - base class for application context in
 * a multi module envirement.
 * </p>
 * Use this class to avoid a common base module in a multi module
 * envirement
 */
public abstract class AbstractApplicationContext
    implements IsContext {

  /* application datastore - available in main- and sub-modules */
  private Context context;

  public AbstractApplicationContext() {
    this.context = new Context();
  }

  /**
   * Gets the application context
   *
   * @return applicaitopn context
   */
  public Context getContext() {
    return this.context;
  }

}

package com.github.nalukit.nalu.client.component;

/**
 * Default implementation of a block controller condition
 */
public class AlwaysShowBlock
    implements IsShowBlockCondition {

  /**
   * Method is called, in case a routing occurs.
   * Depending on the return value, the block will be displayed
   * or not.
   *
   * @param route  the route
   * @param params parameter (0 .. n)
   * @return true: always show the block
   */
  @Override
  public boolean showBlock(String route,
                           String... params) {
    return true;
  }

}

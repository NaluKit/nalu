package com.github.nalukit.nalu.client.component;

/**
 * Marks a block controller condition
 */
public interface IsShowBlockCondition {

  /**
   * Method is called, in case a routing occurs.
   * Depending on the return value, the block will be displayed
   * or not.
   *
   * @param route  the route
   * @param params parameter (0 .. n)
   * @return true: show the block
   */
  boolean showBlock(String route,
                    String... params);

}

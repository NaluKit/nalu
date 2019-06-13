package com.github.nalukit.nalu.client.component;

/**
 * Marks a composite condition
 */
public interface IsLoadCompositeCondition {

  /**
   * Method is called, in case Nalu creates a controller with composites.
   * Dependening on the return value, the composite will be added
   * to the component or not.
   *
   * @param route the route
   * @param parms parameter (0 .. n)
   * @return true: load the composite; false: ignore the composite
   */
  boolean loadComposite(String route,
                        String... parms);

}

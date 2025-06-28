package io.github.nalukit.nalu.client.component;

import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

/**
 * Marks a composite condition
 */
public interface IsLoadCompositeCondition {

  /**
   * Method is called, in case Nalu creates a controller with composites.
   * Depending on the return value, the composite will be added
   * to the component or not.
   *
   * @param route  the route
   * @param params parameter (0 .. n)
   * @return true: load the composite; false: ignore the composite
   */
  @NaluInternalUse
  boolean loadComposite(String route,
                        String... params);

}

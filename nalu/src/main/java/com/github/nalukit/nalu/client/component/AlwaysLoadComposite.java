package com.github.nalukit.nalu.client.component;

/**
 * Default implementation of a composite condition
 */
public class AlwaysLoadComposite
    implements IsLoadCompositeCondition {

  @Override
  public boolean loadComposite(String route,
                               String... parms) {
    return true;
  }

}

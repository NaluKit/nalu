package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.internal.application.DefaultContext;

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

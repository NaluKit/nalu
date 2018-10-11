package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;

@FunctionalInterface
public interface CompositeCreator {

  AbstractCompositeController<?, ?, ?> create(String... params)
      throws RoutingInterceptionException;

}

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;

@FunctionalInterface
public interface ControllerCreator {

  AbstractComponentController<?, ?, ?> create(String... params)
    throws RoutingInterceptionException;

}

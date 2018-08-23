package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.exception.RoutingInterceptionException;

@FunctionalInterface
public interface ControllerCreator {

  AbstractComponentController<?, ?, ?> create(String... params)
    throws RoutingInterceptionException;

}

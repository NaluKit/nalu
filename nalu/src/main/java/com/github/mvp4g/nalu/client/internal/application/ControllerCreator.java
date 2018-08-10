package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.internal.exception.RoutingInterceptionException;
import com.github.mvp4g.nalu.client.ui.AbstractComponentController;

@FunctionalInterface
public interface ControllerCreator {

  AbstractComponentController<?, ?> create(String... params) throws RoutingInterceptionException;

}

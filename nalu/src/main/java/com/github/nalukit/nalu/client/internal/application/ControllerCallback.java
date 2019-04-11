package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;

public interface ControllerCallback {

  void onRoutingInterceptionException(RoutingInterceptionException e);

  void onFinish(ControllerInstance controllerInstance);

}

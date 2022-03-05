package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public interface ControllerCallback {

  void onRoutingInterceptionException(RoutingInterceptionException e);

  void onFinish(ControllerInstance controllerInstance);

}

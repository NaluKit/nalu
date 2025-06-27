package io.github.nalukit.nalu.client.internal.application;

import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public interface ControllerCallback {

  void onRoutingInterceptionException(RoutingInterceptionException e);

  void onFinish(ControllerInstance controllerInstance);

}

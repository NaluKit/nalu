package io.github.nalukit.nalu.client.internal.application;

import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public interface ShellCallback {

  void onFinish(ShellInstance shellInstance);

  void onShellNotFound();

  void onRoutingInterceptionException(RoutingInterceptionException e);

}

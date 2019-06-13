package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public interface ShellCallback {

  void onFinish(ShellInstance shellInstance);

  void onShellNotFound();

  void onRoutingInterceptionException(RoutingInterceptionException e);

}

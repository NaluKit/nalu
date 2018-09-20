package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractSplitterController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;

@FunctionalInterface
public interface SplitterCreator {

  AbstractSplitterController<?, ?, ?> create(String... params)
    throws RoutingInterceptionException;

}

package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.ui.AbstractComponent;

@FunctionalInterface
public interface ComponentCreator {

  AbstractComponent create();

}

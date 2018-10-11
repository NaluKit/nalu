package com.github.nalukit.nalu.client.component.annotation;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Composite {

  String name();

  Class<? extends AbstractCompositeController<?, ?, ?>> compositeController();

  String selector();

}

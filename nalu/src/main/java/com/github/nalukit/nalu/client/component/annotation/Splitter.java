package com.github.nalukit.nalu.client.component.annotation;

import com.github.nalukit.nalu.client.component.AbstractSplitterController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Splitter {

  String name();

  Class<? extends AbstractSplitterController<?, ?, ?>> splitterController();

  String selector();

}

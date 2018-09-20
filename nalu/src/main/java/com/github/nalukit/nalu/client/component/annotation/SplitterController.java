package com.github.nalukit.nalu.client.component.annotation;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.IsComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SplitterController {

  Class<? extends IsComponent<?, ?>> componentInterface();

  Class<? extends AbstractComponent<?, ?>> component();

}

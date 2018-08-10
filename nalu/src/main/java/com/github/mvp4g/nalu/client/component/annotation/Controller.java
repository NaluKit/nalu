package com.github.mvp4g.nalu.client.component.annotation;


import com.github.mvp4g.nalu.client.component.AbstractComponent;
import com.github.mvp4g.nalu.client.component.IsComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an calls in nalu-route. It defines the route and the selector.</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>route: name of the route which will display the controller in case of calling</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

  String route();

  String selector();

  Class<? extends IsComponent<?>> componentInterface();

  Class<? extends AbstractComponent<?>> component();

}

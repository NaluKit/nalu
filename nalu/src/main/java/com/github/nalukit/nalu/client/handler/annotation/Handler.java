package com.github.nalukit.nalu.client.handler.annotation;

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
public @interface Handler {
}

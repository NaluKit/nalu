package com.github.mvp4g.nalu.react.ui.annotations;


import com.github.mvp4g.nalu.react.NaluReact;
import com.github.mvp4g.nalu.react.internal.NoPlace;
import com.github.mvp4g.nalu.react.ui.IsPlace;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an calls in nalu-react. It defines the route and the selector.</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>route: name of the route which will display the view in case of calling</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {

  String route();

  Class<? extends IsPlace> place() default NoPlace.class;

  String selector() default NaluReact.NALU_ID_BODY;

}

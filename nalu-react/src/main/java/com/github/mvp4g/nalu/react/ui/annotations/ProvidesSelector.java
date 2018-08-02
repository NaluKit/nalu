package com.github.mvp4g.nalu.react.ui.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an class in nalu-react. It names the selector provided by the view</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>selector: list of Strings that names the selectors provided by this view</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidesSelector {

  String[] selector();

}

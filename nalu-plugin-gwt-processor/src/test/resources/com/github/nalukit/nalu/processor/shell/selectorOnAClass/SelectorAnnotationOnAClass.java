package com.github.nalukit.nalu.processor.shell.selectorOnAClass;

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalukit.nalu.processor.common.MockContext;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
@Shell("shellName")
@Selector("content")
public class SelectorAnnotationOnAClass
    extends AbstractShell<MockContext> {
  
  public SelectorAnnotationOnAClass() {
    super();
  }
  
  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the shell to the browser body.
   */
  @Override
  public void attachShell() {
  }
  
  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We remove the shell from the browser body.
   */
  @Override
  public void detachShell() {
  }
  
}

package com.github.nalukit.nalu.processor.shell.shellWithComposite04;

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.processor.common.MockContext;
import elemental2.dom.*;
import jsinterop.base.Js;
import java.lang.Override;

@Shell("mockShell")
@Composites(@Composite(name = "shellComposite", compositeController = MockComposite.class, selector = "shellComposte"))
public class ShellWithComposite
    extends AbstractShell<MockContext> {

  private HTMLDivElement shell;

  public ShellWithComposite() {
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the ShellView to the browser body.
   */
  @Override
  public void attachShell() {
    DomGlobal.document.body.appendChild(this.render());
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We remmove the ShellView from the browser body.
   */
  @Override
  public void detachShell() {
    DomGlobal.document.body.removeChild(this.shell);
  }

  private HTMLElement render() {
    DomGlobal.document.body.style.margin = CSSProperties.MarginUnionType.of(0);

    HTMLDivElement navigation = this.createDiv();
    navigation.id = "navigation";
    HTMLDivElement shellComposte = this.createDiv();
    shellComposte.id = "shellComposte";
    HTMLDivElement content = this.createDiv();
    content.id = "content";

    this.shell = this.createDiv();
    this.shell.appendChild(createNorth())
              .appendChild(createSouth())
              .appendChild(navigation)
              .appendChild(shellComposte)
              .appendChild(content);

    return this.shell;
  }

  private Element createNorth() {
    return this.createDiv();
  }

  private Element createSouth() {
    return this.createDiv();
  }

  private HTMLDivElement createDiv() {
    return (HTMLDivElement) Js.cast(DomGlobal.document.createElement("div"));
  }

}

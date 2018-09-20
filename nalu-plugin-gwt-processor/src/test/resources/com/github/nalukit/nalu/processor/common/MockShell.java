package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.Nalu;
import com.github.nalukit.nalu.client.component.AbstractShell;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.*;

public class MockShell
  extends AbstractShell<MockContext> {

  public MockShell() {
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the ShellView to the browser body.
   */
  @Override
  public void attachShell() {
    document.body.appendChild(this.render());
  }


  private HTMLElement render() {
    return div().css("shell")
                .add(createNorth())
                .add(createSouth())
                .add(div().css("shellNavigation")
                          .attr(Nalu.NALU_ID_ATTRIBUTE,
                                "navigation")
                          .asElement())
                .add(div().css("shellContent")
                          .attr(Nalu.NALU_ID_ATTRIBUTE,
                                "content")
                          .asElement())
                .asElement();
  }

  private Element createNorth() {
    return header().css("shellHeader")
                   .attr(Nalu.NALU_ID_ATTRIBUTE,
                         "header")
                   .asElement();
  }

  private Element createSouth() {
    return footer().css("shellFooter")
                   .attr(Nalu.NALU_ID_ATTRIBUTE,
                         "footer")
                   .asElement();
  }
}

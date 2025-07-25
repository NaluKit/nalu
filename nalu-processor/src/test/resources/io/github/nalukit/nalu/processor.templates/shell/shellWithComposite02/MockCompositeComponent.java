package io.github.nalukit.nalu.processor.shell.shellWithComposite02;

import io.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.lang.Override;

import static elemental2.dom.DomGlobal.document;

public class MockCompositeComponent
    extends AbstractCompositeComponent<IMockCompositeComponent.Controller, HTMLElement>
    implements IMockCompositeComponent {

  public MockCompositeComponent() {
  }

  @Override
  public void render() {
    HTMLDivElement divElemet = (HTMLDivElement) document.createElement("div");
    initElement(divElemet);
  }
}

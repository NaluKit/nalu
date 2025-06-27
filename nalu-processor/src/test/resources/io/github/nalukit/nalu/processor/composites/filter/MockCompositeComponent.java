package io.github.nalukit.nalu.processor.composites.filter;

import io.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;
import java.lang.Override;

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

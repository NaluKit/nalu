package com.github.nalukit.nalu.processor.compositeCreator.ok;

import com.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import com.github.nalukit.nalu.processor.compositeCreator.ok.ICompositeComponent.Controller;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;

public class CompositeComponent
    extends AbstractCompositeComponent<Controller, HTMLElement>
    implements ICompositeComponent {

  public CompositeComponent() {
  }

  @Override
  public void render() {
    HTMLDivElement divElemet = (HTMLDivElement) document.createElement("div");
    ;
    initElement(divElemet);
  }
}

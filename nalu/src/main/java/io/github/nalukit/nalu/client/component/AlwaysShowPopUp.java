package io.github.nalukit.nalu.client.component;

import io.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import io.github.nalukit.nalu.client.context.IsContext;

/**
 * Default implementation of a composite condition
 */
public class AlwaysShowPopUp
    extends AbstractPopUpCondition<IsContext> {

  public AlwaysShowPopUp() {
  }

  @Override
  public boolean showPopUp(ShowPopUpEvent event) {
    return true;
  }

}

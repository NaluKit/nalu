package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;

/**
 * Default implementation of a composite condition
 */
public class AlwaysShowPopUp
    extends AbstractPopUpCondition {

  public AlwaysShowPopUp() {
  }

  @Override
  public boolean showPopUp(ShowPopUpEvent event) {
    return true;
  }

}

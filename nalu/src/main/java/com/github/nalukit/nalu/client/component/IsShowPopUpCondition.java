package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

/**
 * Marks a popup condition
 */
public interface IsShowPopUpCondition {

  /**
   * Method is called, in case <b>ShowPopUpEvent</b> is fired.
   * <p>
   * Depending on the return value, the event is canceled or not.
   *
   * @param event the @see ShowPopUpEvent
   * @return true: show the popup; false: do not show the popup
   */
  @NaluInternalUse
  boolean showPopUp(ShowPopUpEvent event);

}

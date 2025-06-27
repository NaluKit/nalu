package io.github.nalukit.nalu.client.plugin;

import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.ConfirmHandler;

public interface IsCustomConfirmPresenter {

  /**
   * Show confirm using the message
   *
   * @param message message to display
   */
  void confirm(String message);

  /**
   * Add a ConfirmHandler to the confirm
   * <p>
   * The dialog implementing this method needs to make sure,
   * that the CondirmHandler gets removed after the OK or Cancel
   * button gets pressed!
   *
   * @param confirmHandler confirm handler
   */
  void addConfirmHandler(ConfirmHandler confirmHandler);

}

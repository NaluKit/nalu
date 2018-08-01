/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.mvp4g.nalu.eventBus;

public interface IsEventBus {

  //  /**
  //   * Fires the Start event!
  //   */
  //  void fireStartEvent();
  //
  //  /**
  //   * Fires the InitHistory event
  //   */
  //  void fireInitHistoryEvent();
  //
  //  /**
  //   * Fires the NotFoundHistory event
  //   */
  //  void fireNotFoundHistoryEvent();
  //
  //  /**
  //   * Framework method to set the shell
  //   * <br>
  //   * <b>Do not use!</b>
  //   */
  //  void setShell();
  //
  //  /**
  //   * Adds a presenter instance to the eventBus and using the bind-parameter <b>true</b>.
  //   * <br><br>
  //   * Using this feature requires that the presetner has set "multiple" to true!
  //   * <br><br>
  //   *
  //   * @param presenter instance of the new presenter to add to the eventbus
  //   * @return returns a PresenterRegistration - to remove the presenter registration from the eventbus
  //   */
  //  PresenterRegistration addHandler(IsPresenter<?, ?> presenter);
  //
  //  /**
  //   * Adds a presenter instance to the eventBus.
  //   * <br><br>
  //   * Using this feature requires that the presetner has set "multiple" to true!
  //   * <br><br>
  //   *
  //   * @param presenter instance of the new presenter to add to the eventbus
  //   * @param bind      true - the bind- and createView-method of the view will be called in case the presenter is added to the eventbus
  //   *                  false - the bind- and createView-method of the view will be called in case first event is fired!
  //   * @return returns a PresenterRegistration - to remove the presenter registration from the eventbus
  //   */
  //  PresenterRegistration addHandler(IsPresenter<?, ?> presenter,
  //                                   boolean bind);
  //
  ////  /**
  ////   * Method to manually ask if an action can occur
  ////   *
  ////   * @param event event to be executed in case the presenter does not interrupt navigation
  ////   */
  //  //  void confirmNavigation(NavigationEventCommand event);
  //
  //  IsNavigationConfirmation getNavigationConfirmationPresenter();
  //
  //  /**
  //   * Set a confirmation that will be called before each navigation event or when history token
  //   * changes. This will set the navigationConfirmationPresenter for the whole application. You can have
  //   * only one navigationConfirmationPresenter for the whole application.
  //   *
  //   * @param navigationConfirmationPresenter presenter which should be called in case of confirmation
  //   */
  //  void setNavigationConfirmation(IsNavigationConfirmation navigationConfirmationPresenter);
  //
  //  /**
  //   * <p><b>Internal method!<br><br> DO NOT USE!</b></p>
  //   * <p>Sets the place service</p>
  //   *
  //   * @param placeService the new palce service
  //   */
  //  void setPlaceService(PlaceService<? extends IsEventBus> placeService);
  //
  //  /**
  //   * Add a new event filter
  //   *
  //   * @param filter new event filter to add
  //   */
  //  void addEventFilter(IsEventFilter<? extends IsEventBus> filter);
  //
  //  /**
  //   * Remove event filter
  //   *
  //   * @param filter event filter to remove
  //   */
  //  void removeEventFilter(IsEventFilter<? extends IsEventBus> filter);

}

/*
 * Copyright (C) 2016 Frank Hossfeld <frank.hossfeld@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithLoader;

import com.github.mvp4g.mvp4g2.core.eventbus.PresenterRegistration;
import com.github.mvp4g.mvp4g2.core.internal.eventbus.AbstractEventBus;
import com.github.mvp4g.mvp4g2.core.ui.IsPresenter;


public class MockEventBusImpl
  extends AbstractEventBus<MockEventBus>
  implements MockEventBus {

  public MockEventBusImpl() {
    super("de.gishmo.gwt.example.mvp4g2.simpleapplication.core.ui.shell.ShellPresenter",
          true);
  }

  public void oneEvent() {
  }

  protected void loadDebugConfiguration() {
  }

  protected void loadFilterConfiguration() {
  }

  protected void loadEventHandlerMetaData() {
  }

  protected void loadEventMetaData() {
  }

  public void fireStartEvent() {
  }

  public void fireInitHistoryEvent() {
  }

  public void fireNotFoundHistoryEvent() {
  }

  @Override
  public PresenterRegistration addHandler(IsPresenter<?, ?> presenter) throws
                                                                       Mvp4g2RuntimeException {
    List<PresenterMetaData<?, ?>> presenters = super.presenterMetaDataMap.get(presenter.class.getCanonialName());
    for (PresenterMetaData presenterMetaData : presenters.values()) {
      if (!presenterMetaData.isMultiple()) {
        throw new Mvp4g2RuntimeException(presenter.class.getCanonialName() + ": can not be used with the addHandler()-method, because it is not defined as multiple presenter!");
      }
    }
    return null;
  }

  public PresenterRegistration addHandler(IsPresenter<?, ?> presenter) {
    return new PresenterRegistration() {
      public void remove() {

      }
    };
  }

}

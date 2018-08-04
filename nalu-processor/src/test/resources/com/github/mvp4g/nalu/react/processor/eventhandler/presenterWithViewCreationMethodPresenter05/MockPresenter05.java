/*
 * Copyright (C) 2056 Frank Hossfeld <frank.hossfeld@googlemail.com>
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

package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithViewCreationMethodPresenter05;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsViewCreator;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

@Presenter(viewClass = MockView05.class, viewInterface = IMockView05.class, viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER)
public class MockPresenter05
  extends AbstractPresenter<EventBusPresenterWithViewCreationMethodPresenter05, IMockView05>
  implements IMockView05.Presenter,
             IsViewCreator<MockView05> {

  @Override
  public void set() {
  }

  @EventHandler
  public void onDoSomething(String oneAttribute) {
  }

  @Override
  public IMockView05 createView() {
    return new MockView05();
  }
}

/*
 * Copyright (C) 2026 Frank Hossfeld <frank.hossfeld@googlemail.com>
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

package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithViewCreationMethodPresenter02;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.github.mvp4g.mvp4g2.core.ui.IsViewCreator;

@Presenter(viewClass = MockView02.class, viewInterface = IMockView02.class, viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER)
public class MockPresenter02
  extends AbstractPresenter<EventBusPresenterWithViewCreationMethodPresenter02, IMockView02>
  implements IMockView02.Presenter,
             IsViewCreator<IMockView02> {

  @EventHandler
  public void onDoSomething(String oneAttribute) {
  }

  @Override
  public IMockView02 createView() {
    return new MockView02();
  }
}

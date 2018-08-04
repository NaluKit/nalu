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

package com.github.mvp4g.nalu.react.processor.event.eventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsShell;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

@Presenter(viewClass = MockShellView.class, viewInterface = IMockShellView.class, viewCreator = Presenter.VIEW_CREATION_METHOD.FRAMEWORK)
public class MockShellPresenter
  extends AbstractPresenter<EventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature, IMockShellView>
  implements IMockShellView.Presenter,
             IsShell<EventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature, IMockShellView> {

  @Override
  public void setShell() {
  }

  public void onEvent01() {
  }
}

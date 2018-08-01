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

package com.github.mvp4g.nalu.ui;

import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

/**
 * Presenters marked with this interface will
 * create the view instance.
 * <br>
 * Make sure, that viewCreator attribute of the presenter annotation is
 * set to Presenter.viewCerator.VIEW_CREATION_METHOD
 * <br><br>
 * In case using this interface, there must be createView method, that looks like that:
 * <code>
 * public void createView() {
 * view = GWT.create(IMyView.class);
 * }
 * </code>
 *
 * @param <V> generator of view
 * @see Presenter#viewCreator() is set to
 * PRESENTER!
 */
public interface IsViewCreator<V extends IsReactView<?>> {

  V createView();

}

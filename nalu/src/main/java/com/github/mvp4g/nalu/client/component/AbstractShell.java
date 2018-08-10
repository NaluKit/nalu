package com.github.mvp4g.nalu.client.component;

import com.github.mvp4g.nalu.client.application.IsContext;

public abstract class AbstractShell<C extends IsContext>
  extends AbstractController<C>
  implements IsShellController {

  public AbstractShell() {
  }
}

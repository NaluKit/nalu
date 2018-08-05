package com.github.mvp4g.nalu.client.ui;

import com.github.mvp4g.nalu.client.Router;
import com.github.mvp4g.nalu.client.application.IsContext;

public abstract class AbstractController<C extends IsContext> {

  protected Router router;

  protected C context;

  public AbstractController() {
  }

  public void setRouter(Router router) {
    this.router = router;
  }

  public void setContext(C context) {
    this.context = context;
  }
}

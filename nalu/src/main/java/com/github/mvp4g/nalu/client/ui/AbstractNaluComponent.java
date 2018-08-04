package com.github.mvp4g.nalu.client.ui;

import com.github.mvp4g.nalu.client.route.Router;

public abstract class AbstractNaluComponent {

  protected Router router;

  public AbstractNaluComponent() {
  }

  public void setRouter(Router router) {
    this.router = router;
  }
}

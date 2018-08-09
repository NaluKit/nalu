package com.github.mvp4g.nalu.client.filter;

import com.github.mvp4g.nalu.client.application.IsContext;

public abstract class AbstractFilter<C extends IsContext>
  implements IsFilter {

  protected C context;

  public AbstractFilter() {
    super();
  }

  public void setContext(C context) {
    this.context = context;
  }
}

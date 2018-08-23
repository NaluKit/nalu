package com.github.mvp4g.nalu.processor.common;

import com.github.mvp4g.nalu.client.filter.AbstractFilter;

public class MockFilter
  extends AbstractFilter<MockContext> {

  @Override
  public boolean filter(String route,
                        String... parms) {
    return true;
  }

  @Override
  public String redirectTo() {
    return "/search";
  }

  @Override
  public String[] parameters() {
    return new String[]{
    };
  }
}

package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.filter.AbstractFilter;

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

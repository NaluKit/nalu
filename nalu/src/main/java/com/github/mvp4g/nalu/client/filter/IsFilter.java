package com.github.mvp4g.nalu.client.filter;

public interface IsFilter {

  boolean filter(String route,
                 String... parms);

  String redirectTo();

  String[] parameters();

}

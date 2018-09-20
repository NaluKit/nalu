package com.github.nalukit.nalu.client.filter;

public interface IsFilter {

  boolean filter(String route,
                 String... parms);

  String redirectTo();

  String[] parameters();

}

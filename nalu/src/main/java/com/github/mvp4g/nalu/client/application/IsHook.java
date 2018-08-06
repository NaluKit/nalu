package com.github.mvp4g.nalu.client.application;

public interface IsHook {

  boolean hook(String route,
               String... parms);

}

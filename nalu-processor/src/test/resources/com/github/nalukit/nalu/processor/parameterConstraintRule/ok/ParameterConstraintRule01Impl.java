package com.github.nalukit.nalu.processor.parameterConstraintRule.ok;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>gwt-2.8.2-HEAD-SNAPSHOT<< at >>2021.07.29-22:15:45<<
 */
public final class ParameterConstraintRule01Impl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintRule01Impl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.ok.ParameterConstraintRule01";
  }

  @Override
  public boolean isValid(String parameter) {
    return true;
  }
}

package com.github.nalukit.nalu.processor.parameterConstraintRule.ok;

import com.github.nalukit.nalu.client.constrain.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2021.07.28-18:11:25<<
 */
public final class ParameterConstrainRule01Impl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstrainRule01Impl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.ok.ParameterConstrainRule01";
  }

  @Override
  public boolean isValid(String parameter) {
    return true;
  }
}

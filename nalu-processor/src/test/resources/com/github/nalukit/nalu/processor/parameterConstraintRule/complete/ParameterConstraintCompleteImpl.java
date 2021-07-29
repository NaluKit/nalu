package com.github.nalukit.nalu.processor.parameterConstraintRule.complete;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2021.07.29-22:27:53<<
 */
public final class ParameterConstraintCompleteImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintCompleteImpl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.complete.ParameterConstraintComplete";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter == null || parameter.length() == 0) {
      return false;
    }
    return true;
  }
}

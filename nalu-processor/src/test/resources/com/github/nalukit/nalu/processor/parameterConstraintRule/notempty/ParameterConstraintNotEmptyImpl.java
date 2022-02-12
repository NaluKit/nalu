package com.github.nalukit.nalu.processor.parameterConstraintRule.notempty;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>2.8.0<< at >>2021.07.29-22:27:00<<
 */
public final class ParameterConstraintNotEmptyImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintNotEmptyImpl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.notempty.ParameterConstraintNotEmpty";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter == null || parameter.length() == 0) {
      return false;
    }
    return true;
  }
}
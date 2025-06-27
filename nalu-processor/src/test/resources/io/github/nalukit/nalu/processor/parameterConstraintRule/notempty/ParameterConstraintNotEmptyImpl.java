package io.github.nalukit.nalu.processor.parameterConstraintRule.notempty;

import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import io.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2021.07.29-22:27:00<<
 */
public final class ParameterConstraintNotEmptyImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintNotEmptyImpl() {
  }

  @Override
  public String key() {
    return "io.github.nalukit.nalu.processor.parameterConstraintRule.notempty.ParameterConstraintNotEmpty";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter == null || parameter.length() == 0) {
      return false;
    }
    return true;
  }
}
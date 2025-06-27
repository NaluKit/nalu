package io.github.nalukit.nalu.processor.parameterConstraintRule.minLength;

import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import io.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2021.08.03-17:09:33<<
 */
public final class ParameterConstraintMinLengthImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintMinLengthImpl() {
  }

  @Override
  public String key() {
    return "io.github.nalukit.nalu.processor.parameterConstraintRule.minLength.ParameterConstraintMinLength";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter != null && parameter.length() < 2) {
      return false;
    }
    return true;
  }
}

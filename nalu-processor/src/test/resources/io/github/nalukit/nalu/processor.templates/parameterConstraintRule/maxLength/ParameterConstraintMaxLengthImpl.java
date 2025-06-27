package io.github.nalukit.nalu.processor.parameterConstraintRule.maxLength;

import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import io.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2021.07.30-06:47:26<<
 */
public final class ParameterConstraintMaxLengthImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintMaxLengthImpl() {
  }

  @Override
  public String key() {
    return "io.github.nalukit.nalu.processor.parameterConstraintRule.maxLength.ParameterConstraintMaxLength";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter != null && parameter.length() > 8) {
      return false;
    }
    return true;
  }
}

package com.github.nalukit.nalu.processor.parameterConstraintRule.pattern;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.regexp.shared.RegExp;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2021.07.30-17:56:30<<
 */
public final class ParameterConstraintPatternImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintPatternImpl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.pattern.ParameterConstraintPattern";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter != null) {
      RegExp regExp = RegExp.compile("^[0-9]{0,8}?$");
      if (!regExp.test(parameter)) {
        return false;
      }
    }
    return true;
  }
}

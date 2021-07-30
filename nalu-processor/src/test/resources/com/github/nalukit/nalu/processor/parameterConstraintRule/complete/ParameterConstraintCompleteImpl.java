package com.github.nalukit.nalu.processor.parameterConstraintRule.complete;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import org.gwtproject.regexp.shared.RegExp;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2021.07.30-17:57:38<<
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
    if (parameter != null && parameter.length() > 12) {
      return false;
    }
    if (parameter != null) {
      RegExp regExp = RegExp.compile("^[A-Z]?$");
      if (!regExp.test(parameter)) {
        return false;
      }
    }
    if (parameter != null) {
      if (Arrays.asList("A", "B", "C", "D", "E", "F").contains(parameter)) {
        return false;
      }
    }
    if (parameter != null) {
      if (!Arrays.asList("X", "Y", "Z").contains(parameter)) {
        return false;
      }
    }
    return true;
  }
}

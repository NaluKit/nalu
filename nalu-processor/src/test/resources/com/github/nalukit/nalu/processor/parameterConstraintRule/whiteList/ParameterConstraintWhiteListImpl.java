package com.github.nalukit.nalu.processor.parameterConstraintRule.whiteList;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.6.0<< at >>2021.07.30-14:54:55<<
 */
public final class ParameterConstraintWhiteListImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintWhiteListImpl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.whiteList.ParameterConstraintWhiteList";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter != null) {
      if (!Arrays.asList("X", "Y", "Z").contains(parameter)) {
        return false;
      }
    }
    return true;
  }
}

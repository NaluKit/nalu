package com.github.nalukit.nalu.processor.parameterConstraintRule.blackList;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

/**
 * Build with Nalu version >>2.8.0<< at >>2021.07.30-14:48:38<<
 */
public final class ParameterConstraintBlackListImpl extends AbstractParameterConstraintRule implements IsParameterConstraintRule {
  public ParameterConstraintBlackListImpl() {
  }

  @Override
  public String key() {
    return "com.github.nalukit.nalu.processor.parameterConstraintRule.blackList.ParameterConstraintBlackList";
  }

  @Override
  public boolean isValid(String parameter) {
    if (parameter != null) {
      if (Arrays.asList("A", "B", "C", "D", "E", "F").contains(parameter)) {
        return false;
      }
    }
    return true;
  }
}

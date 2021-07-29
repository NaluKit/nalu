/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeModel {
  
  private ClassNameModel provider;
  private ClassNameModel componentInterface;
  private ClassNameModel component;
  private ClassNameModel context;
  private List<ParameterAcceptorModel> parameterAcceptors;
  
  private boolean componentCreator;
  
  public CompositeModel() {
  }
  
  public CompositeModel(ClassNameModel context,
                        ClassNameModel provider,
                        ClassNameModel componentInterface,
                        ClassNameModel component,
                        boolean componentCreator) {
    this.context            = context;
    this.provider           = provider;
    this.componentInterface = componentInterface;
    this.component          = component;
    this.componentCreator   = componentCreator;
    
    this.parameterAcceptors = new ArrayList<>();
  }
  
  public ClassNameModel getContext() {
    return context;
  }
  
  public void setContext(ClassNameModel context) {
    this.context = context;
  }
  
  public ClassNameModel getProvider() {
    return provider;
  }
  
  public void setProvider(ClassNameModel provider) {
    this.provider = provider;
  }
  
  public ClassNameModel getComponentInterface() {
    return componentInterface;
  }
  
  public void setComponentInterface(ClassNameModel componentInterface) {
    this.componentInterface = componentInterface;
  }
  
  public ClassNameModel getComponent() {
    return component;
  }
  
  public void setComponent(ClassNameModel component) {
    this.component = component;
  }
  
  public List<ParameterAcceptorModel> getParameterAcceptors() {
    return parameterAcceptors;
  }

  public ParameterConstraintModel getConstraintModelFor(String parameterName) {
    Optional<ParameterAcceptorModel> optional = this.parameterAcceptors.stream()
                                                                       .filter(a -> parameterName.equals(a.getParameterName()))
                                                                       .findFirst();
    return optional.map(ParameterAcceptorModel::getParameterConstrait)
                   .orElse(null);
  }

  public String getParameterAcceptors(String parameterName) {
    Optional<ParameterAcceptorModel> optional = this.parameterAcceptors.stream()
                                                                       .filter(a -> parameterName.equals(a.getParameterName()))
                                                                       .findFirst();
    return optional.map(ParameterAcceptorModel::getMethodName)
                   .orElse(null);
  }
  
  public boolean isComponentCreator() {
    return componentCreator;
  }
  
  public void setComponentCreator(boolean componentCreator) {
    this.componentCreator = componentCreator;
  }
  
}

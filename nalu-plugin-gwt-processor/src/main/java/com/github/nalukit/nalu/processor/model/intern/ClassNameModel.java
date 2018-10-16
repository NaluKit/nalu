/*
 * Copyright (c) 2018 - Frank Hossfeld
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

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.Objects;

public class ClassNameModel {

  private String className;

  public ClassNameModel(String className) {
    this.className = Objects.requireNonNull(className);
  }

  public String getClassName() {
    return className;
  }

  public TypeName getTypeName() {
    switch (className) {
    case "void":
      return TypeName.VOID;
    case "boolean":
      return TypeName.BOOLEAN;
    case "byte":
      return TypeName.BYTE;
    case "short":
      return TypeName.SHORT;
    case "int":
      return TypeName.INT;
    case "long":
      return TypeName.LONG;
    case "char":
      return TypeName.CHAR;
    case "float":
      return TypeName.FLOAT;
    case "double":
      return TypeName.DOUBLE;
    case "Object":
      return TypeName.OBJECT;
    case "Void":
      return ClassName.get("java.lang",
                           "Void");
    case "Boolean":
      return ClassName.get("java.lang",
                           "Boolean");
    case "Byte":
      return ClassName.get("java.lang",
                           "Byte");
    case "Short":
      return ClassName.get("java.lang",
                           "Short");
    case "Integer":
      return ClassName.get("java.lang",
                           "Integer");
    case "Long":
      return ClassName.get("java.lang",
                           "Long");
    case "Character":
      return ClassName.get("java.lang",
                           "Character");
    case "Float":
      return ClassName.get("java.lang",
                           "Float");
    case "Double":
      return ClassName.get("java.lang",
                           "Double");
    default:
      return ClassName.get(this.getPackage(),
                           this.getSimpleName());
    }
  }

  public String getPackage() {
    return className.contains(".") ? className.substring(0,
                                                         className.lastIndexOf(".")) : "";
  }

  public String getSimpleName() {
    return className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;
  }

  public String normalized() {
    return className.replace(".",
                             "_");
  }

  @Override
  public int hashCode() {
    return Objects.hash(className);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClassNameModel)) {
      return false;
    }
    ClassNameModel that = (ClassNameModel) o;
    return Objects.equals(className,
                          that.className);
  }
}
